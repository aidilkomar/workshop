package com.sein.workshop.controller;

import com.sein.workshop.dto.ApiResponse;
import com.sein.workshop.dto.role.RoleResponseDto;
import com.sein.workshop.dto.user.UserCreateDto;
import com.sein.workshop.dto.user.UserListRequestDto;
import com.sein.workshop.dto.user.UserResponseDto;
import com.sein.workshop.dto.user.UserRolesUpdateDto;
import com.sein.workshop.service.UserService;
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
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    @PreAuthorize("@authz.hasPermission('ROLE_READ')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAll(@RequestBody @Valid UserListRequestDto req) {
        int page = Math.max(req.pagedRequest().page() - 1, 0);
        Pageable pageable = PageRequest.of(
                page,
                req.pagedRequest().size(),
                Sort.by(req.pagedRequest().sortDirection() != null ? req.pagedRequest().sortBy() : "username")
                        .ascending());

        Page<UserResponseDto> pageResult = userService.getUsers(pageable, req.params());
        return ResponseEntity.ok(
                ApiResponse.listSuccess(
                        "success retrieve data user",
                        pageResult.stream().toList(),
                        pageResult.getTotalElements(),
                        pageResult.getTotalPages(),
                        req.pagedRequest().page(),
                        req.pagedRequest().size()
                )
        );
    }

    @GetMapping("/{uuid}/roles")
    @PreAuthorize("@authz.hasPermission('ADMIN_READ')")
    public ResponseEntity<ApiResponse<List<RoleResponseDto>>> getUserRoles(@PathVariable UUID uuid) {
        List<RoleResponseDto> roles = userService.getUserRolesByUserUuid(uuid);
        return ResponseEntity.ok(
                ApiResponse.success("success retrieve user roles", roles, LocalDateTime.now())
        );
    }


    @PostMapping("/create")
    @PreAuthorize("@authz.hasPermission('ADMIN_CREATE')")
    public ResponseEntity<ApiResponse<UserCreateDto>> create(@RequestBody @Valid UserCreateDto req) {
        userService.create(req);
        return ResponseEntity.ok(
                ApiResponse.success("user has created", null, null)
        );
    }

    @PostMapping("/create/{uuid}")
    @PreAuthorize("@authz.hasPermission('ADMIN_CREATE')")
    public ResponseEntity<ApiResponse<UserCreateDto>> createWithRole(@RequestBody @Valid UserCreateDto req, @PathVariable(name = "uuid") UUID roleUuid) {
        userService.addUserWithRole(req, roleUuid);
        return ResponseEntity.ok(
                ApiResponse.success("user has created", null, null)
        );
    }

    @PutMapping("/{uuid}/roles")
    @PreAuthorize("@authz.hasPermission('ADMIN_CREATE')")
    public ResponseEntity<ApiResponse<UserRolesUpdateDto>> addUserRoles(
            @PathVariable UUID uuid,
            @RequestBody @Valid UserRolesUpdateDto req
    ) {
        userService.addUserRoles(uuid, req.roleUuids());
        return ResponseEntity.ok(
                ApiResponse.success("user has updated", null, LocalDateTime.now())
        );
    }
}
