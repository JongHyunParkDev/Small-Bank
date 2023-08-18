package com.dev.was.controller;

import com.dev.was.dto.UserDto;
import com.dev.was.security.AuthenticationUtil;
import com.dev.was.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/userinfo")
    public UserDto getAccounts() {
        String userId = AuthenticationUtil.getCurrentUserId();

        return userService.getUserInfo(userId);
    }
}
