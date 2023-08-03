package com.dev.was.service;

import com.dev.was.entity.AccountEntity;
import com.dev.was.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<AccountEntity> getAccountsByUserIdAndDatetimeBetween(String userId, LocalDate startDate, LocalDate endDate) {
        return accountRepository.findByUserIdAndDatetimeBetween(userId, startDate, endDate);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public AccountEntity saveAccount(AccountEntity accountEntity) {
        return accountRepository.save(accountEntity);
    }
}
