package com.sein.workshop.service;

import com.sein.workshop.dto.role.RoleResponseDto;
import com.sein.workshop.dto.user.UserCreateDto;
import com.sein.workshop.dto.user.UserResponseDto;
import com.sein.workshop.entity.Role;
import com.sein.workshop.entity.User;
import com.sein.workshop.entity.UserRole;
import com.sein.workshop.handler.ConflictException;
import com.sein.workshop.handler.NotFoundException;
import com.sein.workshop.repository.RoleRepository;
import com.sein.workshop.repository.UserRepository;
import com.sein.workshop.repository.UserRoleRepository;
import com.sein.workshop.util.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void create(UserCreateDto req) {
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
        user.setCreatedBy(principal != null ? principal.userId().toString() : null);

        userRepository.save(user);
    }

    @Override
    public Page<UserResponseDto> getUsers(Pageable pageable, String search) {
        Page<User> users;
        if (search == null || search.isEmpty()) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search, pageable);
        }
        return users.map(user -> new UserResponseDto(
                user.getUuid(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt()
        ));
    }

    @Override
    public List<RoleResponseDto> getUserRolesByUserUuid(UUID uuid) {
        return roleRepository.findRoleDtosByUserUuid(uuid);
    }

    @Transactional
    @Override
    public void addUserRoles(UUID uuid, List<UUID> roleUuids) {
        var user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("user not found"));

        userRoleRepository.deleteByUserId(user.getId());

        if (roleUuids == null || roleUuids.isEmpty()) {
            return;
        }

        List<Role> roles = roleRepository.findAllByUuidIn(roleUuids);

        if (roles.size() != roleUuids.size()) {
            throw new InternalException("Invalid role");
        }

        List<UserRole> userRoles = roles.stream()
                .map(r -> new UserRole(user, r))
                .toList();

        userRoleRepository.saveAll(userRoles);
    }

    @Override
    public void addUserWithRole(UserCreateDto req, UUID roleUuid) {
        // implement soon
    }
}
