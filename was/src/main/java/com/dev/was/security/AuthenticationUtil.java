package com.dev.was.security;

import com.dev.was.controller.ApiException;
import com.dev.was.controller.ExceptionCodeEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationUtil
{
    private AuthenticationUtil()
    {
        throw new IllegalStateException("Utility class");
    }

    private static Authentication getAuthentication()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null)
            throw new ApiException(ExceptionCodeEnum.LOGIN_NEED);

        if (!authentication.isAuthenticated())
            throw new ApiException(ExceptionCodeEnum.LOGIN_NEED);

        return authentication;
    }


    public static Long getCurrentId()
    {
        Authentication authentication = getAuthentication();
        return Long.parseLong(authentication.getName());
    }

    public static List<String> getCurrentUserRoles()
    {
        Authentication authentication = getAuthentication();
        Object[] authorities = authentication.getAuthorities().toArray();
        if (authorities.length == 0)
            throw new ApiException(ExceptionCodeEnum.INTERNAL_ERROR);

        List<String> roles = new ArrayList<>();
        for (Object authority : authorities)
        {
            roles.add(((GrantedAuthority)authority).getAuthority());
        }

        return roles;
    }
}

