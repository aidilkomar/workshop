package com.sein.workshop.controller;

import com.sein.workshop.dto.ApiResponse;
import com.sein.workshop.dto.role.*;
import com.sein.workshop.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/")
//    @PreAuthorize("@authz.hasPermission('ADMIN_READ')")
    public ResponseEntity<ApiResponse<List<RoleResponseDto>>> getAll(@RequestBody @Valid RoleListRequestDto req) {
        int page = Math.max(req.pagedRequest().page() - 1, 0);
        Pageable pageable = PageRequest.of(
                page,
                req.pagedRequest().size(),
                Sort.by(
                        req.pagedRequest().sortDirection() != null ? req.pagedRequest().sortBy() : "name")
                        .ascending()
        );

        Page<RoleResponseDto> pageResult = roleService.getRoles(pageable, req.search());
        return ResponseEntity.ok(
                ApiResponse.listSuccess(
                        "success retrieve data roles",
                        pageResult.stream().toList(),
                        pageResult.getTotalElements(),
                        pageResult.getTotalPages(),
                        req.pagedRequest().page(),
                        req.pagedRequest().size()
                )
        );
    }

    @GetMapping("/{uuid}/features")
//    @PreAuthorize("@authz.hasPermission('ADMIN_READ')")
    public ResponseEntity<ApiResponse<List<RoleFeatureResponseDto>>> getRoleFeatures(@PathVariable("uuid") UUID uuid) {
        List<RoleFeatureResponseDto> results = roleService.getRoleFeatures(uuid);
        return ResponseEntity.ok(
                ApiResponse.success("success retrieve role features", results, LocalDateTime.now())
        );
    }

    @PostMapping("/create")
    @PreAuthorize("@authz.hasPermission('ADMIN_CREATE')")
    public ResponseEntity<ApiResponse<RoleCreateDto>> create(@RequestBody @Valid RoleCreateDto req) {
        roleService.create(req);
        return ResponseEntity.ok(
                ApiResponse.success("role has created", req, LocalDateTime.now())
        );
    }

    @PutMapping("/{uuid}/features")
    @PreAuthorize("@authz.hasPermission('ADMIN_UPDATE')")
    public ResponseEntity<ApiResponse<RoleFeaturesUpdateDto>> upsertRoleFeatures(
            @PathVariable UUID uuid,
            @RequestBody @Valid RoleFeaturesUpdateDto req
    ) {
        roleService.upsertRoleFeatures(uuid, req.features());
        return ResponseEntity.ok(
                ApiResponse.success("role features has been created", null, LocalDateTime.now())
        );
    }
}
