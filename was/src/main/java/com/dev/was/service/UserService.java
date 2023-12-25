package com.dev.was.service;

import com.dev.was.controller.ApiException;
import com.dev.was.controller.ExceptionCodeEnum;
import com.dev.was.dto.UserDto;
import com.dev.was.entity.UserEntity;
import com.dev.was.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto joinUserInfo(String email, String password, String name, String phone) {
        if (userRepository.existsByEmail(email))
            throw new ApiException(ExceptionCodeEnum.DUPLICATE_EMAIL);

        UserEntity userEntity = userRepository.save(
                UserEntity.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .name(name)
                        .phone(phone)
                        .role("ROLE_USER")
                        .profileImg("https://ssl.pstatic.net/static/pwe/address/img_profile.png")
                        .build()
        );

        return new UserDto(userEntity);
    }

    public UserDto getUserInfo(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ExceptionCodeEnum.INVALID_ARGUMENT));

        return new UserDto(userEntity);
    }

    public void saveUserInfo(
            Long id,
            String password,
            String phone,
            String name
    ) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(ExceptionCodeEnum.INVALID_ARGUMENT));

        userEntity.setName(name);
        userEntity.setPhone(phone);

        if (password != null) {
            userEntity.setPassword(passwordEncoder.encode(password));
        }

        userRepository.save(userEntity);
    }

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
}
