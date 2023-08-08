package com.dev.was.security;

public enum SecurityErrorCodeEnum {
    LOGIN_REQUIRED("SEC_001", "Login required"),
    INTERNAL_ERROR("SEC_002", "Internal error"),
    INVALID_TOKEN("SEC_003", "Invalid or expired token");

    private final String code;
    private final String message;

    SecurityErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
