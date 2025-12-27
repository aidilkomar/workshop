package com.sein.workshop.controller;

import com.sein.workshop.dto.ApiResponse;
import com.sein.workshop.dto.role.RoleCreateDto;
import com.sein.workshop.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
//    @PreAuthorize("@authz.hasPermission('user_read')")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid RoleCreateDto req) throws Exception {
        roleService.create(req);
        return ResponseEntity.ok(
                ApiResponse.success("role has created", req, LocalDateTime.now())
        );
    }
}
