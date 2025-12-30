package com.sein.workshop.dto.role;

import com.sein.workshop.dto.PagedRequest;

public record RoleListRequestDto(
        PagedRequest pagedRequest,
        String search
) {
}
