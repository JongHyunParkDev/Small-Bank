package com.dev.was.dto;

import com.dev.was.entity.AccountEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDto {
    private Long id;
    private String userId;
    private LocalDate date;
    private String memo;
    private Long money;
    private String time;
    private String type;

    public AccountDto(AccountEntity accountEntity) {
        this.id = accountEntity.getId();
        this.userId = accountEntity.getUserId();
        this.date = accountEntity.getDate();
        this.memo = accountEntity.getMemo();
        this.money = accountEntity.getMoney();
        this.time = accountEntity.getTime();
        this.type = accountEntity.getType();
    }
}
