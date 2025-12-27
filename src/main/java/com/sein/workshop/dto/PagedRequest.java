package com.sein.workshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PagedRequest(
        @NotNull(message = "page is required")
        @Min(value = 0, message = "page must be >= 0")
        int page,

        @NotNull(message = "size is required")
        @Min(value = 1, message = "size must be >= 1")
        int size,

        String sortBy,
        String sortDirection
) {
    public PagedRequest {
        if (sortDirection != null && !sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")){
            throw new IllegalArgumentException("sortDirection must be 'asc' or 'desc'");
        }
    }
}
