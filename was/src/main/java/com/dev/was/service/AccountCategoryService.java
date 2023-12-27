package com.dev.was.service;

import com.dev.was.controller.ApiException;
import com.dev.was.controller.ExceptionCodeEnum;
import com.dev.was.dto.AccountCategoryDto;
import com.dev.was.entity.AccountCategoryEntity;
import com.dev.was.entity.UserEntity;
import com.dev.was.repository.AccountCategoryRepository;
import com.dev.was.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountCategoryService {
    private final AccountCategoryRepository accountCategoryRepository;
    private final UserRepository userRepository;

    public List<AccountCategoryDto> getAccountCategory(
            Long userId
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ExceptionCodeEnum.DB_ERROR, "Not Found User Id"));

        return userEntity.getAccountCategoryEntityList()
                .stream()
                .map(AccountCategoryDto::new)
                .collect(Collectors.toList());
    }

    public AccountCategoryDto saveAccountCategory(
            Long id,
            Long userId,
            String category
    ) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ExceptionCodeEnum.DB_ERROR, "Not Found User Id"));

        AccountCategoryEntity accountCategoryEntity =
                accountCategoryRepository.save(
                        AccountCategoryEntity.builder()
                                .id(id)
                                .category(category)
                                .userEntity(userEntity)
                                .build()
                        );

        return new AccountCategoryDto(accountCategoryEntity);
    }

    public void deleteAccountCategory(Long id) {
        accountCategoryRepository.deleteById(id);
    }
}
