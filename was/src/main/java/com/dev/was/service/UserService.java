package com.dev.was.service;

import com.dev.was.controller.ApiException;
import com.dev.was.controller.ExceptionCodeEnum;
import com.dev.was.dto.UserDto;
import com.dev.was.entity.UserEntity;
import com.dev.was.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getUserInfo(String userId) {
        Optional<UserEntity> findUser = userRepository.findByUserId(userId);

        if (findUser.isEmpty())
            throw new ApiException(ExceptionCodeEnum.INVALID_ARGUMENT);
        return new UserDto(findUser.get());
    }
}
