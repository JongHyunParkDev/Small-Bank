package com.dev.was.vo;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class WeatherDataVo {
    private Response response;

    @Data
    public static class Response {
        private Header header;
        private Body body;
    }

    @Data
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Data
    public static class Items {
        private List<Item> item = new ArrayList<>();
    }

    @Getter
    public static class Item {
        private String baseDate;
        private String baseTime;
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;
        private String nx;
        private String ny;

        public void setBaseDate(String baseDate) {
            this.baseDate = baseDate;
        }

        public void setBaseTime(String baseTime) {
            this.baseTime = baseTime;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setFcstDate(String fcstDate) {
            this.fcstDate = fcstDate;
        }

        public void setFcstTime(String fcstTime) {
            this.fcstTime = fcstTime;
        }

        public void setFcstValue(String fcstValue) {
            if (fcstValue.equals("적설없음") || fcstValue.equals("강수없음")) this.fcstValue = "0";
            else this.fcstValue = fcstValue.split("mm|cm")[0];
        }

        public void setNx(String nx) {
            this.nx = nx;
        }

        public void setNy(String ny) {
            this.ny = ny;
        }
    }
}
