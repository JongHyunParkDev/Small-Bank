package com.dev.pjh.entity;

import com.dev.pjh.vo.AptRentDataVo;
import com.dev.pjh.vo.AptTradeDataVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tm_apt_rent")
public class AptRentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int buildYear;
    private int regionalCode;
    @Column(nullable = false)
    private String dong;
    @Column(nullable = false)
    private String jibun;

    @Column(nullable = false)
    private String apartmentName;
    private int floor;
    private int dealYear;
    private int dealMonth;
    private int dealDay;

    private int deposit;
    private int monthlyRent;
    private double areaForExclusiveUse;

    private int previousDeposit;
    private int previousMonthlyRent;

    private String contractType;

    private String termOfContract;
    private String useRequestRenewalContractRight;

    public AptRentEntity(AptRentDataVo.RentItem item) {
        this.buildYear = item.getBuildYear();
        this.regionalCode = item.getRegionalCode();
        this.dong = item.getDong();
        this.jibun = item.getJibun();
        this.apartmentName = item.getApartmentName();
        this.floor = item.getFloor();
        this.dealYear = item.getDealYear();
        this.dealMonth = item.getDealMonth();
        this.dealDay = item.getDealDay();
        this.deposit = item.getDeposit();
        this.monthlyRent = item.getMonthlyRent();
        this.areaForExclusiveUse = item.getAreaForExclusiveUse();
        this.previousDeposit = item.getPreviousDeposit();
        this.previousMonthlyRent = item.getPreviousMonthlyRent();
        this.contractType = item.getContractType();
        this.termOfContract = item.getTermOfContract();
        this.useRequestRenewalContractRight = item.getUseRequestRenewalContractRight();
    }
}
