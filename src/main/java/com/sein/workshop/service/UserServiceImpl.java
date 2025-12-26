package com.sein.workshop.service;

import com.sein.workshop.dto.user.UserCreateDto;
import com.sein.workshop.entity.User;
import com.sein.workshop.repository.UserRepository;
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
        try {
            if (userRepository.findByUsername(req.username()).isPresent()) {
                throw new RuntimeException("Username already exists");
            }

            if (userRepository.findByEmail(req.email()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }

            var user = new User();
            user.setUsername(req.username());
            user.setPassword(passwordEncoder.encode(req.password()));
            user.setEmail(req.email());

            userRepository.save(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
