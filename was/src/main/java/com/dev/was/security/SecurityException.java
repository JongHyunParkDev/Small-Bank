package com.dev.was.security;

    public class SecurityException extends RuntimeException {
    public String code;

    public SecurityException(SecurityErrorCodeEnum codeEnum) {
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }
}
