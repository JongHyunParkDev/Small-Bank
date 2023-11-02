package com.dev.was.controller;

import lombok.Getter;

@Getter
public enum ExceptionCodeEnum {
    GENERIC(1000, "ERROR"),
    NOT_FOUND(1001, "NOT FOUND"),
    INTERNAL_ERROR(1002, "INTERNAL ERROR"),

    INVALID_REQUEST(1100, "INVALID REQUEST"),
    INVALID_ARGUMENT(1101, "INVALID ARGUMENT"),
    MISSING_REQUIRED_FIELD(1102, "MISSING REQUIRED FIELD"),

    DB_ERROR(1200, "DB ERROR"),

    CALL_DATA_ERROR(1300, "CALL DATA GO API ERROR"),

    LOGIN_NEED(1900, "LOGIN NEED"),
    PERMISSION_DENIED(1901, "PERMISSION DENIED"),

    PW_INVALID(1990, "PW IS NOT VALID"),
    FOUNT_NOT_ID(1991, "FOUND NOT ID"),
    DUPLICATE_EMAIL(1997, "DUPLICATE EMAIL"),
    LOGIN_ERROR(1999, "LOGIN ERROR");

    private final int code;
    private final String message;

    ExceptionCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
