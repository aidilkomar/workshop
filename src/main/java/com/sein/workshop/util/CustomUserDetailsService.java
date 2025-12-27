package com.sein.workshop.util;

import com.sein.workshop.entity.User;
import com.sein.workshop.repository.RoleFeatureRepository;
import com.sein.workshop.repository.UserRepository;
import com.sein.workshop.repository.UserRoleRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleFeatureRepository roleFeatureRepository;
    private final PermissionCache permissionCache;

    public CustomUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository, RoleFeatureRepository roleFeatureRepository, PermissionCache permissionCache) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleFeatureRepository = roleFeatureRepository;
        this.permissionCache = permissionCache;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Invalid credential"));

        List<String> roles = userRoleRepository.findRoleCodesByUserId(user.getId());
        List<String> perms = roleFeatureRepository.findPermissionByUserId(user.getId());

        CustomUserDetails userDetails =
                new CustomUserDetails(
                        user.getId(), user.getUuid(), user.getUsername(), user.getPassword(),
                        roles.stream().map(SimpleGrantedAuthority::new).toList(),
                        perms.stream().map(SimpleGrantedAuthority::new).toList()
                );

        roles.forEach(role -> {
            Set<String> permissionsForRole = new HashSet<>(perms);
            permissionCache.setPermission(role, permissionsForRole);
        });

        return userDetails;
    }
}
