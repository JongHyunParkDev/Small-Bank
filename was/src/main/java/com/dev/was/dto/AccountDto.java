package com.dev.was.dto;

import com.dev.was.entity.AccountEntity;
import com.dev.was.util.CommonUtil;
import lombok.Data;

@Data
public class AccountDto {
    private Long id;
    private Long userId;
    private String date;
    private String time;
    private String memo;
    private Long money;
    private String category;
    private String type;

    public AccountDto(AccountEntity accountEntity) {
        this.id = accountEntity.getId();
        this.userId = accountEntity.getUserId();
        this.date = accountEntity.getDate().format(CommonUtil.LOCAL_DATE_FORMATTER);
        this.time = accountEntity.getTime();
        this.memo = accountEntity.getMemo();
        this.money = accountEntity.getMoney();
        this.category = accountEntity.getCategory();
        this.type = accountEntity.getType();
    }
}
