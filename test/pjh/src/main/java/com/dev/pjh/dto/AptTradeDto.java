package com.dev.pjh.dto;

import lombok.Data;

@Data
public class AptTradeDto {
    private int dealAmount;
    private String reqGbn;
    private int buildYear;
    private int dealYear;
    private String dong;
    private String apartmentName;
    private int dealMonth;
    private int dealDay;
    private double areaForExclusiveUse;
    private String rdealerLawdnm;
    private String jibun;
    private int regionalCode;
    private int floor;
    private String cancelDealDay;
    private String cancelDealType;
}
