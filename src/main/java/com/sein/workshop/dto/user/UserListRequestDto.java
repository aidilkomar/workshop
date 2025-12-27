package com.sein.workshop.dto.user;

import com.sein.workshop.dto.PagedRequest;

public record UserListRequestDto(
        PagedRequest pagedRequest,
        String params
) {
}
