package com.sein.workshop.service;

import com.sein.workshop.dto.auth.LoginRequest;
import com.sein.workshop.repository.UserRepository;
import com.sein.workshop.util.CustomUserDetails;
import com.sein.workshop.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public String login(LoginRequest req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.username(),
                        req.password()
                )
        );

        log.info("authentication: {}", authentication.getPrincipal());

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        log.info("user details: {}", userDetails);

        assert userDetails != null;
        return jwtUtils.generateToken(
                userDetails.getUsername(),
                userDetails.getId(),
                userDetails.getUuid(),
                userDetails.getRoles()
//                userDetails.getAuthorities()
        );
    }
}
