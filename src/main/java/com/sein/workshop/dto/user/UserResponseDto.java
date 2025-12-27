package com.sein.workshop.dto.user;


import java.time.OffsetDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID uuid,
        String username,
        String email,
        OffsetDateTime createdAt
) {}
