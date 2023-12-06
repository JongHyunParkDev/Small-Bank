package com.dev.was.security;

import com.dev.was.controller.ExceptionCodeEnum;
import jakarta.servlet.ServletException;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final OAuth2MemberService oAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable);

        http
            .authorizeHttpRequests((authz) -> authz
                    .requestMatchers(
                            "/",
                            "/api/anon/**",
                            "/index.html",
                            "/favicon.ico",
                            "/icons/**",
                            "/assets/**"
                    ).permitAll()
                    .requestMatchers(
                            "/api/user/**")
                    .authenticated()
                    .requestMatchers(
                            "/api/admin/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated());

        http
            .logout((logoutConfig) -> logoutConfig
                    .logoutUrl("/api/user/logout")
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .permitAll());

        http
            .formLogin((formLogin) -> formLogin
                    .usernameParameter("email")
                    .passwordParameter("pw")
                    .loginProcessingUrl("/api/anon/login")
                    .successHandler(formLoginSuccessHandler())
                    .failureHandler(loginFailHandler())
            );

        http
            .oauth2Login((oauth) -> oauth
                    .userInfoEndpoint((userInfo) ->
                            userInfo.userService(oAuth2MemberService))
                    .successHandler(oauthLoginSuccessHandler())
                    .failureHandler(loginFailHandler())
            );

        http
            .exceptionHandling((exceptionHandling) -> exceptionHandling
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            );

        return http.build();
    }

    @Bean
    public LoginFailHandler loginFailHandler() {
        return new LoginFailHandler();
    }
    public static class LoginFailHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            logger.error("login fail handler");
            CustomAuthenticationException ce = ((CustomAuthenticationException) exception);

            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            String data = "{\"code\":" + ce.getCode() + ",\"message\":\"" + ce.getMessage()  + "\"}";

            // /oauth2/authorization/{domain}
            if (request.getRequestURI().contains("oauth")) {
                String redirectUrl = "/#/login?error=" +
                        URLEncoder.encode(data, StandardCharsets.UTF_8.toString());
                response.sendRedirect(redirectUrl);
            }
            else {
                PrintWriter out = response.getWriter();
                out.print(data);
                out.flush();
                out.close();
            }
        }
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler();
    }

    public static class FormLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(
                HttpServletRequest request, HttpServletResponse response, Authentication auth) {
            // form success login 시 redirect 를 막기 위한 핸들러
            try {
                HttpSession session = request.getSession();

            } catch (Exception e) {
                // 여기까지 왔으면 이미 로그인은 성공된 경우임.
                logger.error("handle login success error.", e);
            }
        }
    }

    @Bean
    public OauthLoginSuccessHandler oauthLoginSuccessHandler() {
        return new OauthLoginSuccessHandler();
    }

    public static class OauthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(
                HttpServletRequest request, HttpServletResponse response, Authentication auth) {
            // oauth 는 login 시 외부에서 redirect 를 해줌으로 필요.
            try {
                HttpSession session = request.getSession();
                String sessionId = session.getId();
                String userId = auth.getName();
                String oauthPostfixPath = "/#/oauthCallback";

                String url = request.getScheme() + "://" + request.getServerName() + oauthPostfixPath;
                response.sendRedirect(url);

            } catch (Exception e) {
                // 여기까지 왔으면 이미 로그인은 성공된 경우임.
                logger.error("handle login success error.", e);
            }
        }
    }

    @Bean
    public AuthenticationEntryPoint CustomAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    // 인증되지 않은 사용자가 보호된 리소스에 접근하려고 할 때 발생하는 예외를 처리하기 위한 인터페이스
    public static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            String data = "{\"code\": " + ExceptionCodeEnum.LOGIN_NEED.getCode() + "}";

            PrintWriter out = response.getWriter();
            out.print(data);
            out.flush();
            out.close();
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
}