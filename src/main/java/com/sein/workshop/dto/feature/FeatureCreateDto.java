package com.sein.workshop.dto.feature;

import jakarta.validation.constraints.NotBlank;

public record FeatureCreateDto(
        @NotBlank(message = "code is required") String code,
        String icon,
        String name,
        String path,
        int sortOrder
) {
}
