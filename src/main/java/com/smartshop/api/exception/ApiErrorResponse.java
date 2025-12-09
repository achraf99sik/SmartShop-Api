package com.smartshop.api.exception;

public record ApiErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {}
