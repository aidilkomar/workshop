package com.sein.workshop.service;

import com.sein.workshop.dto.ApiResponse;
import com.sein.workshop.dto.user.UserCreateDto;

public interface UserService {
    void create(UserCreateDto req) throws Exception;
}
