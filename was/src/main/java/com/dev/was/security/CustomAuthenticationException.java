package com.dev.was.security;

import com.dev.was.controller.ExceptionCodeEnum;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException
{
    public CustomAuthenticationException(ExceptionCodeEnum exceptionCodeEnum)
    {
        super(exceptionCodeEnum.getMessage());
        this.code = exceptionCodeEnum.getCode();
    }

    public CustomAuthenticationException(ExceptionCodeEnum exceptionCodeEnum, String addMsg)
    {
        super(exceptionCodeEnum.getMessage() + " " + addMsg);
        this.code = exceptionCodeEnum.getCode();
    }

    public int getCode()
    {
        return code;
    }

    private final int code;

    private static final long serialVersionUID = 1L;
}
