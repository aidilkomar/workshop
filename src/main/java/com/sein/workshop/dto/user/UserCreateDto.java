package com.sein.workshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(
        @NotBlank(message = "username is required") String username,
        @NotBlank(message = "password is required") String password,
        @Email(message = "incorrect email") String email
) {}
