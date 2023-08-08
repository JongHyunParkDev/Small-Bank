package com.dev.was.security;

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
            throw new SecurityException(SecurityErrorCodeEnum.LOGIN_REQUIRED);

        if (!authentication.isAuthenticated())
            throw new SecurityException(SecurityErrorCodeEnum.LOGIN_REQUIRED);

        return authentication;
    }


    public static String getCurrentUserId()
    {
        Authentication authentication = getAuthentication();
        return authentication.getName();
    }

    public static List<String> getCurrentUserRoles()
    {
        Authentication authentication = getAuthentication();
        Object[] authorities = authentication.getAuthorities().toArray();
        if (authorities.length == 0)
            throw new SecurityException(SecurityErrorCodeEnum.INTERNAL_ERROR);

        List<String> roles = new ArrayList<>();
        for (Object authority : authorities)
        {
            roles.add(((GrantedAuthority)authority).getAuthority());
        }

        return roles;
    }
}

