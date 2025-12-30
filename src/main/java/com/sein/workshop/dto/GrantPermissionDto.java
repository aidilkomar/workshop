package com.sein.workshop.dto;

public record GrantPermissionDto(
        Long roleId,
        Long featureId
) {}

