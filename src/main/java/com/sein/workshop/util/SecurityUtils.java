package com.sein.workshop.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static CustomUserDetails getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            return null;
        }

        return (CustomUserDetails) auth.getPrincipal();
    }

    public static List<String> getCurrentRoles() {
        CustomUserDetails user = getCurrentUser();
        if (user == null) return List.of();

        return user.getRoles().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public static List<String> getCurrentPermissions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) return List.of();

        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }
}

