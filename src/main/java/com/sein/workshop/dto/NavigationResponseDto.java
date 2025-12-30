package com.sein.workshop.dto;

public record NavigationResponseDto(
        String feature,
        String path,
        String icon,
        Integer sortOrder
) {
}
