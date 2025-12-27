package com.sein.workshop.service;

import com.sein.workshop.dto.ApiResponse;
import com.sein.workshop.dto.user.UserCreateDto;
import com.sein.workshop.dto.user.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void create(UserCreateDto req) throws Exception;

    Page<UserResponseDto> getUsers(Pageable pageable, String params);
}
