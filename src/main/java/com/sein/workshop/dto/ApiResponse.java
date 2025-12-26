package com.sein.workshop.dto;

public record ApiResponse<T>(
    boolean success,
    String message,
    T data,
    Object meta
) {
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
