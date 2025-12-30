package com.sein.workshop.dto.role;

import java.util.UUID;

public record RoleFeaturePermissionDto(
        UUID featureUuid,
        boolean canRead,
        boolean canWrite,
        boolean canUpdate,
        boolean canDelete,
        boolean canApprove,
        boolean canExport
) {
}
