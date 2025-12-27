package com.sein.workshop.dto;

import com.sein.workshop.entity.Action;

public record GrantPermissionDto(
        Long roleId,
        Long featureId,
        Action action
) {}

