package com.sein.workshop.dto.user;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record UserRolesUpdateDto(
        @NotEmpty(message = "role ids required")
        List<UUID> roleUuids
) {
}
