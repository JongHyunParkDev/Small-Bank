package com.dev.was.security;

import com.dev.was.controller.ExceptionCodeEnum;
import com.dev.was.entity.UserEntity;
import com.dev.was.scheduleTask.DataGoScheduleTask;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticatorProvider implements AuthenticationProvider {
    private final PrincipalDetailsService principalDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName(); // 사용자가 입력한 id
        String password = (String) authentication.getCredentials(); // 사용자가 입력한 password

        PrincipalDetails principalDetails =
                (PrincipalDetails) principalDetailsService.loadUserByUsername(username);


        if (principalDetails == null) {
            logger.info("[{}] is not valid", username);
            throw new CustomAuthenticationException(ExceptionCodeEnum.FOUNT_NOT_ID);
        }

        String dbPassword = principalDetails.getPassword();

        if(! passwordEncoder.matches(password, dbPassword)) {
            logger.info("[{}] password not match", username);
            throw new CustomAuthenticationException(ExceptionCodeEnum.PW_INVALID);
        }

        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticatorProvider.class);
}