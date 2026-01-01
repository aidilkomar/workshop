package com.sein.workshop.dto.role;

import java.util.UUID;

public record RoleFeatureResponseDto(
    String name,
    boolean canWrite,
    boolean canRead,
    boolean canUpdate,
    boolean canDelete,
    boolean canApprove,
    boolean canExport
) {
}
