package com.sein.workshop.util;

import com.sein.workshop.entity.User;
import com.sein.workshop.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        List<String> roles = List.of("admin", "user", "superadmin");
        List<String> perms = List.of("user_read", "user_approve");

        return new CustomUserDetails(
                user.getId(),
                user.getUuid(),
                user.getUsername(),
                user.getPassword(),
                roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList(),
                perms.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList()
        );
    }
}
