package com.dev.was.dto;

import com.dev.was.entity.AccountCategoryEntity;
import lombok.Data;

@Data
public class AccountCategoryDto {
    private Long id;

    private String category;

    public AccountCategoryDto(AccountCategoryEntity accountCategoryEntity) {
        this.id = accountCategoryEntity.getId();
        this.category = accountCategoryEntity.getCategory();
    }
}
