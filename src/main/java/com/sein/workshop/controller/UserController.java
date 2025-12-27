package com.sein.workshop.controller;

import com.sein.workshop.dto.ApiResponse;
import com.sein.workshop.dto.user.UserCreateDto;
import com.sein.workshop.dto.user.UserListRequestDto;
import com.sein.workshop.dto.user.UserResponseDto;
import com.sein.workshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getUsers(@Valid @RequestBody UserListRequestDto req) {
        Pageable pageable = PageRequest.of(req.pagedRequest().page(), req.pagedRequest().size(),
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


    @PostMapping("/create")
    @PreAuthorize("@authz.hasPermission('user_read')")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid UserCreateDto req) throws Exception {
        userService.create(req);
        return ResponseEntity.ok(
                ApiResponse.success("user has created", null, null)
        );
    }
}
