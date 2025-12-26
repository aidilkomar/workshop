package com.sein.workshop.dto.user;


import java.time.OffsetDateTime;

public record UserResponseDto(
        String username,
        String email,
        OffsetDateTime createdAt
) {}
