package com.sein.workshop.service;

import com.sein.workshop.dto.user.UserCreateDto;
import com.sein.workshop.entity.User;
import com.sein.workshop.handler.ConflictException;
import com.sein.workshop.repository.UserRepository;
import com.sein.workshop.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void create(UserCreateDto req) throws Exception {
        var principal = SecurityUtils.getCurrentUser();

        if (userRepository.findByUsername(req.username()).isPresent()) {
            throw new ConflictException("Username already exists");
        }

        if (userRepository.findByEmail(req.email()).isPresent()) {
            throw new ConflictException("Email already exists");
        }

        var user = new User();
        user.setUsername(req.username());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setEmail(req.email());
        user.setCreatedBy(principal != null ? principal.getId().toString() : null);

        userRepository.save(user);
    }
}
