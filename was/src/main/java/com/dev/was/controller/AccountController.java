package com.dev.was.controller;

import com.dev.was.dto.AccountDto;
import com.dev.was.entity.AccountEntity;
import com.dev.was.security.AuthenticationUtil;
import com.dev.was.service.AccountService;
import com.dev.was.util.DBUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/accounts")
    public List<AccountEntity> getAccounts(
        @RequestParam(value = "startDate") String startDate,
        @RequestParam(value = "endDate") String endDate) {
        String userId = AuthenticationUtil.getCurrentUserId();

        return accountService.getAccountsByUserIdAndDatetimeBetween(userId,
                DBUtil.toLocalDate(startDate), DBUtil.toLocalDate(endDate));
    }
}
