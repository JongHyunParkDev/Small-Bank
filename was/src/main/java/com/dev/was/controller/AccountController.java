package com.dev.was.controller;

import com.dev.was.dto.AccountDto;
import com.dev.was.entity.AccountEntity;
import com.dev.was.security.AuthenticationUtil;
import com.dev.was.service.AccountService;
import com.dev.was.util.DBUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/accounts")
    public List<AccountDto> getAccounts(
        @RequestParam(value = "startDate") String startDate,
        @RequestParam(value = "endDate") String endDate) {
        String userId = AuthenticationUtil.getCurrentUserId();

        return accountService.getAccountsByUserIdAndDatetimeBetween(userId,
                DBUtil.toLocalDate(startDate), DBUtil.toLocalDate(endDate));
    }

    @PostMapping("/add")
    public AccountDto addAccount(@RequestBody @Valid RequestAddAccountDto requestAddAccountDto) {
        String userId = AuthenticationUtil.getCurrentUserId();

        return accountService.saveAccount(
                null,
                userId,
                DBUtil.toLocalDate(requestAddAccountDto.date),
                requestAddAccountDto.memo,
                requestAddAccountDto.money,
                requestAddAccountDto.type,
                requestAddAccountDto.time
        );
    }

    @PutMapping("/update")
    public AccountDto updateAccount(@RequestBody @Valid RequestUpdateAccountDto requestUpdateAccountDto) {
        String userId = AuthenticationUtil.getCurrentUserId();

        return accountService.saveAccount(
                requestUpdateAccountDto.id,
                userId,
                DBUtil.toLocalDate(requestUpdateAccountDto.date),
                requestUpdateAccountDto.memo,
                requestUpdateAccountDto.money,
                requestUpdateAccountDto.type,
                requestUpdateAccountDto.time
        );
    }

    @DeleteMapping("/delete")
    public void deleteAccount(@RequestBody @Valid RequestDeleteAccountDto requestDeleteAccountDto) {
        accountService.deleteAccount(requestDeleteAccountDto.id);
    }

    private class RequestAddAccountDto {
        @NotNull
        private String date;
        @NotBlank
        private String memo;
        @NotNull
        private Long money;
        @NotBlank
        private String time;
        @NotBlank
        private String type;
    }

    private class RequestUpdateAccountDto {
        @NotNull
        private Long id;
        @NotNull
        private String date;
        @NotBlank
        private String memo;
        @NotNull
        private Long money;
        @NotBlank
        private String time;
        @NotBlank
        private String type;
    }

    private class RequestDeleteAccountDto {
        @NotNull
        private Long id;
    }

}
