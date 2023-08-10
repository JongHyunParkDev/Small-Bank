package com.dev.was.service;

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
            // TODO API Exception 추가시 작업
            throw new RuntimeException("Failed find user");
        return new UserDto(findUser.get());
    }
}
