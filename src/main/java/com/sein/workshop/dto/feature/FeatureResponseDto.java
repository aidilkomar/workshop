package com.sein.workshop.dto.feature;

import java.util.UUID;

public record FeatureResponseDto(
        UUID uuid,
        String code,
        String name,
        String path,
        Integer sortOrder,
        String icon
) {
}
