package com.sein.workshop.dto;

public record PaginationMeta(
        long totalItems,
        int totalPages,
        int page,
        int size) {
}
