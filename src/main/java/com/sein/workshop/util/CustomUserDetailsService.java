package com.sein.workshop.util;

import com.sein.workshop.entity.User;
import com.sein.workshop.repository.RolePermissionRepository;
import com.sein.workshop.repository.UserRepository;
import com.sein.workshop.repository.UserRoleRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public CustomUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository, RolePermissionRepository rolePermissionRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Invalid credential"));

        List<String> roles = userRoleRepository.findRoleCodesByUserId(user.getId());
        List<String> perms = rolePermissionRepository.findPermissionCodesByUserId(user.getId());

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
