package com.dev.was.controller.anon;

import com.dev.was.dto.UserDto;
import com.dev.was.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/anon")
public class AnonController {
    private final UserService userService;

    @PostMapping("/joinUser")
    public UserDto joinUser(@RequestBody @Valid RequestJoinUserDto requestJoinUserDto) {
        return userService.joinUserInfo(
                requestJoinUserDto.email,
                requestJoinUserDto.password,
                requestJoinUserDto.name,
                requestJoinUserDto.phone
            );
    }

    public static class RequestJoinUserDto {
        @NotBlank
        public String email;
        @NotBlank
        public String password;
        @NotBlank
        public String name;
        @NotBlank
        public String phone;
    }
}