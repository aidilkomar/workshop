package com.sein.workshop.util;

import com.sein.workshop.handler.MissingAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component("authz")
public class AuthorizationService {
    public boolean hasPermission(String permission) {
        return getAuthorities().contains(permission);
    }

    public boolean hasAnyPermission(String... permissions) {
        Set<String> auths = getAuthorities();
        return Arrays.stream(permissions).anyMatch(auths::contains);
    }

    public boolean hasAllPermissions(String... permissions) {
        Set<String> auths = getAuthorities();
        return Arrays.stream(permissions).allMatch(auths::contains);
    }

    private Set<String> getAuthorities() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new MissingAuthenticationException("No authentication found in Security Context");
        }
        return auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }
}
