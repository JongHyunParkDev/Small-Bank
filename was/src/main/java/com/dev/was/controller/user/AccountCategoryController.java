package com.dev.was.controller.user;

import com.dev.was.dto.AccountCategoryDto;
import com.dev.was.security.AuthenticationUtil;
import com.dev.was.service.AccountCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AccountCategoryController {
    private final AccountCategoryService accountCategoryService;

    @GetMapping("/accountCategory")
    public List<AccountCategoryDto> getAccountCategory() {
        Long userId = AuthenticationUtil.getCurrentId();

        return accountCategoryService.getAccountCategory(userId);
    }

    @PostMapping("/accountCategory")
    public AccountCategoryDto saveAccountCategory(
            @RequestBody @Valid RequestAddAccountCategoryDto requestAddAccountCategoryDto) {
        Long userId = AuthenticationUtil.getCurrentId();

        return accountCategoryService.saveAccountCategory(
                userId,
                requestAddAccountCategoryDto.category);
    }

    @DeleteMapping("/accountCategory")
    public void deleteAccountCategory(
            @RequestBody @Valid RequestDeleteAccountCategoryDto requestDeleteAccountCategoryDto) {

        accountCategoryService.deleteAccountCategory(requestDeleteAccountCategoryDto.id);
    }

    public static class RequestAddAccountCategoryDto {
        @NotNull
        public String category;
    }

    public static class RequestDeleteAccountCategoryDto {
        @NotNull
        public Long id;
    }
}
