package com.dev.pjh.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// AptDataVo 구조를 사용하려면,
// AptDataVo 를 상속 받고 정적 멤버 클래스에 Item 을 상속받고 상세 내역을 구현 해야한다.
@Data
public class AptDataVo<T> {
    private Response<T> response;

    @Data
    public static class Response<T> {
        private Header header;
        private Body<T> body;
    }

    @Data
    public static class Header {
        private String resultCode; // 결과 코드
        private String resultMsg; // 결과 메시지
    }

    @Data
    public static class Body<T> {
        // items 객체
        private Items<T> items;
        // numOfRows, pageNo, totalCount
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Data
    public static class Items<T> {
        private List<T> item;
    }

    // 얘가 있어야 다형성 성립하게 만들 수 있다.
    // 공통된 column 이 있지만, 공공데이터 포털에서 데이터를 바꿀 수도 있다고
    // 두 API 가 같이 변경된다는 가정이 있어야 함으로, 아무것도 두지않는다.
    @Data
    public static class Item {

    }
}
