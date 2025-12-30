package com.sein.workshop.dto.role;

import java.util.List;

public record RoleFeaturesUpdateDto(
        List<RoleFeaturePermissionDto> features
) {
}
