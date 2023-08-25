package com.dev.was.controller;

import lombok.Getter;

@Getter
public enum ExceptionCodeEnum {
    GENERIC(1000, "ERROR"),
    NOT_FOUND(1001, "NOT_FOUND"),
    INTERNAL_ERROR(1002, "INTERNAL_ERROR"),

    INVALID_REQUEST(1100, "INVALID_REQUEST"),
    INVALID_ARGUMENT(1101, "INVALID_ARGUMENT"),
    MISSING_REQUIRED_FIELD(1102, "MISSING_REQUIRED_FIELD"),

    DB_ERROR(1200, "DB_ERROR"),
    LOGIN_NEED(1900, "LOGIN_NEED"),
    PERMISSION_DENIED(1901, "PERMISSION_DENIED");

    private final int code;
    private final String message;

    ExceptionCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
