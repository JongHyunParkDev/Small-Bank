package com.dev.pjh.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AptTradeDataVo extends AptDataVo<AptTradeDataVo.Item> {

    @Data
    public static class TradeItem extends AptDataVo.Item {
        @JsonProperty("건축년도")
        private int buildYear;
        @JsonProperty("지역코드")
        private int regionalCode;
        @JsonProperty("법정동")
        private String dong;
        @JsonProperty("지번")
        private String jibun;
        @JsonProperty("아파트")
        private String apartmentName;
        @JsonProperty("층")
        private int floor;
        @JsonProperty("년")
        private int dealYear;
        @JsonProperty("월")
        private int dealMonth;
        @JsonProperty("일")
        private int dealDay;
        @JsonProperty("거래금액")
        private int dealAmount;

        @JsonProperty("거래유형")
        private String reqGbn;
        @JsonProperty("전용면적")
        private double areaForExclusiveUse;
        @JsonProperty("중개사소재지")
        private String rdealerLawdnm;
        @JsonProperty("해제사유발생일")
        private String cancelDealDay;
        @JsonProperty("해제여부")
        private String cancelDealType;

        public void setDealAmount(String dealAmount) {
            this.dealAmount = Integer.parseInt(dealAmount.trim().replace(",", ""));
        }

        public void setReqGbn(String reqGbn) {
            this.reqGbn = convertStr(reqGbn);
        }

        public void setCancelDealDay(String cancelDealDay) {
            this.cancelDealDay = convertStr(cancelDealDay);
        }

        public void setCancelDealType(String cancelDealType) {
            this.cancelDealType = convertStr(cancelDealType);
        }

        public void setDong(String dong) {
            this.dong = convertStr(dong);
        }

        public void setApartmentName(String apartmentName) {
            this.apartmentName = convertStr(apartmentName);
        }

        public void setRdealerLawdnm(String rdealerLawdnm) {
            this.rdealerLawdnm = convertStr(rdealerLawdnm);
        }

        public String convertStr(String str) {
            return str.trim();
        }
    }
}
