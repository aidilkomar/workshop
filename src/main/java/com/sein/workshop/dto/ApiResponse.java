package com.sein.workshop.dto;

import java.util.List;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data,
    Object meta
) {
    public static <T> ApiResponse<List<T>> listSuccess(String message, List<T> data, long totalItems, int totalPages, int page, int size) {
        PaginationMeta meta = new PaginationMeta(totalItems, totalPages, page, size);
        return new ApiResponse<>(true, message, data, meta);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, null);
    }

    public static <T> ApiResponse<T> success(String message, T data, Object meta) {
        return new ApiResponse<>(true, message, data, meta);
    }

    public static ApiResponse<Object> error(String message, Object errors) {
        return new ApiResponse<>(false, message, null, errors);
    }
}
