package com.sein.workshop.dto.role;

import java.util.UUID;

public record RoleResponseDto (
        UUID uuid,
        String code,
        String name
) {
}
