package com.dev.was.service;

import com.dev.was.dto.AccountDto;
import com.dev.was.entity.AccountEntity;
import com.dev.was.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<AccountDto> getAccountsByUserIdAndDateBetween(String userId, LocalDate startDate, LocalDate endDate) {
        List<AccountEntity> accountEntityList = accountRepository.findByUserIdAndDateBetween(userId, startDate, endDate);

        List<AccountDto> accountDtoList = new ArrayList<>();
        accountEntityList.forEach(entity -> {
            accountDtoList.add(new AccountDto(entity));
        });

        return accountDtoList;
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public AccountDto saveAccount(
            Long id,
            String userId,
            LocalDate date,
            String time,
            String memo,
            Long money,
            String category,
            String type
            ) {
        AccountEntity saveAccountEntity = AccountEntity.builder()
                .id(id)
                .userId(userId)
                .date(date)
                .memo(memo)
                .money(money)
                .time(time)
                .type(type)
                .build();

        AccountEntity accountEntity = accountRepository.save(saveAccountEntity);
        return new AccountDto(accountEntity);
    }
}
