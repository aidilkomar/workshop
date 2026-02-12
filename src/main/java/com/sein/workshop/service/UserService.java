package com.sein.workshop.service;

import com.sein.workshop.dto.role.RoleResponseDto;
import com.sein.workshop.dto.user.UserCreateDto;
import com.sein.workshop.dto.user.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void create(UserCreateDto req);

    Page<UserResponseDto> getUsers(Pageable pageable, String params);

    List<RoleResponseDto> getUserRolesByUserUuid(UUID uuid);

    void addUserRoles(UUID uuid, List<UUID> uuids);

    void addUserWithRole(@Valid UserCreateDto req, UUID roleUuid);
}
