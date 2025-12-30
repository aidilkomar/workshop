package com.sein.workshop.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public record UserDetailsDto(
        String sub,
        Long userId,
        UUID uuid,
        Collection<? extends GrantedAuthority> roles,
        Collection<? extends GrantedAuthority> perms
) {}
