package com.sein.workshop.service;

import com.sein.workshop.dto.role.RoleCreateDto;
import com.sein.workshop.dto.role.RoleFeaturePermissionDto;
import com.sein.workshop.dto.role.RoleFeatureResponseDto;
import com.sein.workshop.dto.role.RoleResponseDto;
import com.sein.workshop.entity.Feature;
import com.sein.workshop.entity.Role;
import com.sein.workshop.entity.RoleFeature;
import com.sein.workshop.handler.ConflictException;
import com.sein.workshop.handler.NotFoundException;
import com.sein.workshop.repository.FeatureRepository;
import com.sein.workshop.repository.RoleFeatureRepository;
import com.sein.workshop.repository.RoleRepository;
import com.sein.workshop.util.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final FeatureRepository featureRepository;

    private final RoleFeatureRepository roleFeatureRepository;

    public RoleServiceImpl(RoleRepository roleRepository, FeatureRepository featureRepository, RoleFeatureRepository roleFeatureRepository) {
        this.roleRepository = roleRepository;
        this.featureRepository = featureRepository;
        this.roleFeatureRepository = roleFeatureRepository;
    }

    @Override
    public void create(RoleCreateDto req) {
        var principal = SecurityUtils.getCurrentUser();

        var existCode = roleRepository.existsByCode(req.code());
        if (existCode) {
            throw new ConflictException("The code already exist");
        }

        var entity = new Role();
        entity.setCode(req.code());
        entity.setName(req.name());
        entity.setCreatedBy(principal != null ? principal.userId().toString() : null);

        roleRepository.save(entity);
    }

    @Override
    public Page<RoleResponseDto> getRoles(Pageable pageable, String search) {
        Page<Role> roles = (search == null || search.isBlank())
                ? roleRepository.findAll(pageable)
                : roleRepository.findAllByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(search, search, pageable);

        return roles.map(role ->
                new RoleResponseDto(
                        role.getUuid(),
                        role.getCode(),
                        role.getName()
                )
        );
    }

    @Override
    public List<RoleFeatureResponseDto> getRoleFeatures(UUID uuid) {
        var roleFeatures = roleFeatureRepository.findAllFeaturesByUuid(uuid);
        if (!roleFeatures.isEmpty())
            return roleFeatures;
        return List.of();
    }

    @Transactional
    @Override
    public void upsertRoleFeatures(UUID uuid, List<RoleFeaturePermissionDto> req) {
        // find role
        Role role = roleRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("role not found"));

        // find all feature
        Map<UUID, Feature> featureMap = featureRepository
                .findByUuidIn(
                        req.stream()
                                .map(RoleFeaturePermissionDto::featureUuid)
                                .toList()
                )
                .stream()
                .collect(Collectors.toMap(Feature::getUuid, Function.identity()));

        // find feature by role
        Map<UUID, RoleFeature> existingMap = roleFeatureRepository
                .findByRole(role)
                .stream()
                .collect(Collectors.toMap(
                        rf -> rf.getFeature().getUuid(),
                        Function.identity()
                ));

        List<RoleFeature> toSave = new ArrayList<>();

        for (RoleFeaturePermissionDto dto : req) {
            Feature feature = featureMap.get(dto.featureUuid());
            if (feature == null) {
                throw new NotFoundException("feature not found: " + dto.featureUuid());
            }

            RoleFeature rf = existingMap.getOrDefault(
                    dto.featureUuid(),
                    new RoleFeature()
            );

            rf.setRole(role);
            rf.setFeature(feature);

            rf.setCanRead(dto.canRead());
            rf.setCanWrite(dto.canWrite());
            rf.setCanUpdate(dto.canUpdate());
            rf.setCanDelete(dto.canDelete());
            rf.setCanApprove(dto.canApprove());
            rf.setCanExport(dto.canExport());

            toSave.add(rf);
        }

        roleFeatureRepository.saveAll(toSave);
    }
}
