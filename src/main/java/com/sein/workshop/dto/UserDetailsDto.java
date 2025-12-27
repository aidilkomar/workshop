package com.sein.workshop.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public record UserDetailsDto(
        String sub,
        Long id,
        UUID uuid,
        Collection<? extends GrantedAuthority> roles,
        Collection<? extends GrantedAuthority> perms
) {}
