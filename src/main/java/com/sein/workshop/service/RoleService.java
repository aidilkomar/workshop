package com.sein.workshop.service;

import com.sein.workshop.dto.role.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    void create(RoleCreateDto req);

    Page<RoleResponseDto> getRoles(Pageable pageable, String search);

    void upsertRoleFeatures(UUID uuid, List<RoleFeaturePermissionDto> req);

    List<RoleFeatureResponseDto> getRoleFeatures(UUID uuid);
}
