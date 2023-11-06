package com.dev.was.controller.user;

import com.dev.was.dto.UserDto;
import com.dev.was.security.AuthenticationUtil;
import com.dev.was.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/userinfo")
    public UserDto getUserInfo() {
        Long id = AuthenticationUtil.getCurrentId();

        return userService.getUserInfo(id);
    }

    @PutMapping("/userinfo")
    public void updateUserInfo(@RequestBody @Valid RequestUpdateUserDto requestUpdateUserDto) {
        Long id = AuthenticationUtil.getCurrentId();

        userService.saveUserInfo(id,
                requestUpdateUserDto.password, requestUpdateUserDto.phone, requestUpdateUserDto.name);
    }
    public static class RequestUpdateUserDto {
        public String password;
        @NotBlank
        public String phone;
        @NotBlank
        public String name;
    }
}
