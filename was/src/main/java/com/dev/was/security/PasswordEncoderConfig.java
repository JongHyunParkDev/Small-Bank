package com.dev.was.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder customPasswordEncoder()
    {
        // 참고: https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#pe-dpe
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        // 위 참고의 StandardPasswordEncoder의 sha265표준과 다른 형태라서 md-sha256 이라고 만들었다.
        encoders.put("md-sha256", new MessageDigestPasswordEncoder("SHA-256"));
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
}
