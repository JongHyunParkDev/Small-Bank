package com.dev.was.dto;

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
}
