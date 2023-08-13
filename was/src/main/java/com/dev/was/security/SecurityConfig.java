package com.dev.was.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login.html").permitAll()
                .requestMatchers("/api/user/**").authenticated() // user 시작하는 uri는 로그인 필수
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // admin 시작하는 uri는 관리자 계정만 접근 가능
                .anyRequest().authenticated() //나머지 uri는 모든 접근 허용
                .and()
                .logout()
                .logoutUrl("/api/user/logout") // URL mapping for logout
                .logoutSuccessUrl("/login.html") // Redirect to login page after successful logout
                .invalidateHttpSession(true) // Invalidate HTTP session after logout
                .clearAuthentication(true) // Clear authentication information after logout
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login.html") //로그인이 필요한데 로그인을 하지 않았다면 이동할 uri 설정
                .successHandler(loginSuccessHandler()) //OAuth 로그인이 성공하면 이동할 uri 설정
                .userInfoEndpoint()//로그인 완료 후 회원 정보 받기
                .userService(oAuth2MemberService).and().and().build();
    }


    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    public static class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(
                HttpServletRequest request, HttpServletResponse response, Authentication auth) {
            try {
                HttpSession session = request.getSession();
                String sessionId = session.getId();
                String userId = auth.getName();

            } catch (Exception e) {
                // 여기까지 왔으면 이미 로그인은 성공된 경우임.
                logger.error("handle login success error.", e);
            }
        }

    }
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
}