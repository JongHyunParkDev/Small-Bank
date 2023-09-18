package com.dev.was.vo;

import lombok.Data;

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
        private String baseDate;
        private String baseTime;
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;
        private String nx;
        private String ny;
    }
}
