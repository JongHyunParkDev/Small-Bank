package com.dev.pjh.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AptRentDataVo {
    private Response response;

    @Data
    public static class Response {
        private Header header;
        private Body body;
    }

    @Data
    public static class Header {
        private String resultCode; // 결과 코드
        private String resultMsg; // 결과 메시지
    }

    @Data
    public static class Body {
        // items 객체
        private Items items;
        // numOfRows, pageNo, totalCount
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Data
    public static class Items {
        private List<Item> item = new ArrayList<>();
    }

    @Data
    public static class Item {
        // item 객체 내의 멤버 변수
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

        @JsonProperty("보증금액")
        private int deposit;
        @JsonProperty("월세금액")
        private int monthlyRent;
        @JsonProperty("전용면적")
        private double areaForExclusiveUse;

        @JsonProperty("종전계약보증금")
        private int previousDeposit;
        @JsonProperty("종전계약월세")
        private int previousMonthlyRent;

        @JsonProperty("계약구분")
        private String contractType;
        @JsonProperty("계약기간")
        private String termOfContract;
        @JsonProperty("갱신요구권사용")
        private String useRequestRenewalContractRight;


        public void setDeposit(String deposit) {
            this.deposit = convertIntByStringEmpty(deposit);
        }

        public void setPreviousDeposit(String previousDeposit) {
            this.previousDeposit = convertIntByStringEmpty(previousDeposit);
        }

        public void setUseRequestRenewalContractRight(String useRequestRenewalContractRight) {
            this.useRequestRenewalContractRight = convertStr(useRequestRenewalContractRight);
        }

        public void setContractType(String contractType) {
            this.contractType = convertStr(contractType);
        }

        public void setTermOfContract(String termOfContract) {
            this.termOfContract = convertStr(termOfContract);
        }

        public void setDong(String dong) {
            this.dong = convertStr(dong);
        }

        public void setJibun(String jibun) {
            this.jibun = convertStr(jibun);
        }

        public void setApartmentName(String apartmentName) {
            this.apartmentName = convertStr(apartmentName);
        }

        public int convertIntByStringEmpty(String str) {
            String result = str.trim().replace(",", "");
            return result.isEmpty() ? 0 : Integer.parseInt(result);
        }
        public String convertStr(String str) {
            return str.trim();
        }
    }
}