package com.dev.pjh.entity;

import com.dev.pjh.vo.AptTradeDataVo;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tm_apt_trade")
public class AptTradeEntity {
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
    @Column(nullable = false)
    private int dealAmount;

    private String reqGbn;
    private double areaForExclusiveUse;
    private String rdealerLawdnm;
    @Column(nullable = false)
    private String cancelDealDay;
    @Column(nullable = false)
    private String cancelDealType;

    public AptTradeEntity(AptTradeDataVo.Item item) {
        this.dealAmount = item.getDealAmount();
        this.reqGbn = item.getReqGbn();
        this.buildYear = item.getBuildYear();
        this.dealYear = item.getDealYear();
        this.dong = item.getDong();
        this.apartmentName = item.getApartmentName();
        this.dealMonth = item.getDealMonth();
        this.dealDay = item.getDealDay();
        this.areaForExclusiveUse = item.getAreaForExclusiveUse();
        this.rdealerLawdnm = item.getRdealerLawdnm();
        this.jibun = item.getJibun();
        this.regionalCode = item.getRegionalCode();
        this.floor = item.getFloor();
        this.cancelDealDay = item.getCancelDealDay();
        this.cancelDealType = item.getCancelDealType();
    }
}
