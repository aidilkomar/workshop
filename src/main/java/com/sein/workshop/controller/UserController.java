package com.sein.workshop.controller;

import com.sein.workshop.dto.ApiResponse;
import com.sein.workshop.dto.user.UserCreateDto;
import com.sein.workshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @PreAuthorize("@authz.hasPermission('user_read')")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid UserCreateDto req) throws Exception {
        userService.create(req);
        return ResponseEntity.ok(
                ApiResponse.success("user has created", null, null)
        );
    }
}
