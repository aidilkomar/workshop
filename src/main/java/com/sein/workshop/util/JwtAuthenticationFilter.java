package com.sein.workshop.util;

import com.sein.workshop.dto.UserDetailsDto;
import com.sein.workshop.repository.RoleFeatureRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final PermissionCache permissionCache;

    private final RoleFeatureRepository roleFeatureRepository;

    public JwtAuthenticationFilter(JwtUtils jwtUtils, PermissionCache permissionCache, RoleFeatureRepository roleFeatureRepository) {
        this.jwtUtils = jwtUtils;
        this.permissionCache = permissionCache;
        this.roleFeatureRepository = roleFeatureRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateToken(jwt)) {
                String username = jwtUtils.getUsernameFromJwt(jwt);
                UUID uuid = jwtUtils.getUserUuid(jwt);
                Long id = jwtUtils.getId(jwt);

                Collection<? extends GrantedAuthority> roles = jwtUtils.getRoles(jwt);

                Set<String> permissions = permissionCache.getPermission(id.toString());


                if (permissions.isEmpty()) {
                    permissions = new HashSet<>(roleFeatureRepository.findPermissionByUserId(id));
                    permissionCache.setPermission(id.toString(), permissions);
                }

                Collection<? extends GrantedAuthority> authorities = permissions.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                var userDetails = new UserDetailsDto(username, id, uuid, roles, authorities);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                authorities
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);
            }
        } catch (Exception _) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
