package com.sein.workshop.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long id;
    @Getter
    private final UUID uuid;
    private final String username;
    private final String password;
    @Getter
    private final Collection<? extends GrantedAuthority> roles;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(
            Long id,
            UUID uuid,
            String username,
            String password,
            Collection<? extends GrantedAuthority> roles,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.id = id;
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
