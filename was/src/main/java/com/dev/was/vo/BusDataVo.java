package com.dev.was.vo;

import lombok.Builder;
import lombok.Data;


// Response 받아서 mapping 하려고 헀으나, xml 의 comMsgHeader properties 와 list 선언에 properties 가 정상적이지 않아
// String Mapping 이후 값을 생성한다.
@Data
public class BusDataVo {
    private String flag;
    private String locationNo1;
    private String locationNo2;
    private String lowPlate1;
    private String lowPlate2;
    private String plateNo1;
    private String plateNo2;
    private String predictTime1;
    private String predictTime2;
    private String remainSeatCnt1;

    @Builder
    public BusDataVo(String flag, String locationNo1, String locationNo2, String lowPlate1, String lowPlate2, String plateNo1, String plateNo2, String predictTime1, String predictTime2, String remainSeatCnt1, String remainSeatCnt2, String routeId, String staOrder, String stationId) {
        this.flag = flag;
        this.locationNo1 = locationNo1;
        this.locationNo2 = locationNo2;
        this.lowPlate1 = lowPlate1;
        this.lowPlate2 = lowPlate2;
        this.plateNo1 = plateNo1;
        this.plateNo2 = plateNo2;
        this.predictTime1 = predictTime1;
        this.predictTime2 = predictTime2;
        this.remainSeatCnt1 = remainSeatCnt1;
        this.remainSeatCnt2 = remainSeatCnt2;
        this.routeId = routeId;
        this.staOrder = staOrder;
        this.stationId = stationId;
    }

    private String remainSeatCnt2;
    private String routeId;
    private String staOrder;
    private String stationId;
}
