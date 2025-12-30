package com.sein.workshop.service;

import com.sein.workshop.dto.role.RoleCreateDto;
import com.sein.workshop.dto.role.RoleFeaturePermissionDto;
import com.sein.workshop.dto.role.RoleFeaturesUpdateDto;
import com.sein.workshop.dto.role.RoleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    void create(RoleCreateDto req);

    Page<RoleResponseDto> getRoles(Pageable pageable, String search);

    void upsertRoleFeatures(UUID uuid, List<RoleFeaturePermissionDto> req);
}
