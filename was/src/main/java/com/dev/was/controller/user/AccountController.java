package com.dev.was.controller.user;

import com.dev.was.dto.AccountDto;
import com.dev.was.security.AuthenticationUtil;
import com.dev.was.service.AccountService;
import com.dev.was.util.DBUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/accounts")
    public List<AccountDto> getAccounts(
        @RequestParam(value = "startDate") String startDate,
        @RequestParam(value = "endDate") String endDate) {
        String userId = AuthenticationUtil.getCurrentUserId();

        return accountService.getAccountsByUserIdAndDateBetween(userId,
                DBUtil.toLocalDate(startDate), DBUtil.toLocalDate(endDate));
    }

    @PostMapping("/accounts")
    public AccountDto addAccount(@RequestBody @Valid RequestAddAccountDto requestAddAccountDto) {
        String userId = AuthenticationUtil.getCurrentUserId();

        return accountService.saveAccount(
                null,
                userId,
                DBUtil.toLocalDate(requestAddAccountDto.date),
                requestAddAccountDto.time,
                requestAddAccountDto.memo,
                requestAddAccountDto.money,
                requestAddAccountDto.category,
                requestAddAccountDto.type
        );
    }

    @PutMapping("/accounts")
    public AccountDto updateAccount(@RequestBody @Valid RequestUpdateAccountDto requestUpdateAccountDto) {
        String userId = AuthenticationUtil.getCurrentUserId();

        return accountService.saveAccount(
                requestUpdateAccountDto.id,
                userId,
                DBUtil.toLocalDate(requestUpdateAccountDto.date),
                requestUpdateAccountDto.time,
                requestUpdateAccountDto.memo,
                requestUpdateAccountDto.money,
                requestUpdateAccountDto.category,
                requestUpdateAccountDto.type
        );
    }

    @DeleteMapping("/accounts")
    public void deleteAccount(@RequestBody @Valid RequestDeleteAccountDto requestDeleteAccountDto) {
        accountService.deleteAccount(requestDeleteAccountDto.id);
    }

    public static class RequestAddAccountDto {
        @NotNull
        public String date;
        @NotBlank
        public String time;
        @NotBlank
        public String memo;
        @NotNull
        public Long money;
        @NotBlank
        public String category;
        @NotBlank
        public String type;
    }

    public static class RequestUpdateAccountDto {
        @NotNull
        public Long id;
        @NotNull
        public String date;
        @NotBlank
        public String time;
        @NotBlank
        public String memo;
        @NotNull
        public Long money;
        @NotBlank
        public String category;
        @NotBlank
        public String type;
    }

    public static class RequestDeleteAccountDto {
        @NotNull
        public Long id;
    }

}
