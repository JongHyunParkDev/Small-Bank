package com.dev.was.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;


@Data
public class SerialDto {
    private boolean isState;
    private boolean isMsgState;
    private Map<String, List<Map<String, String>>> busMap;
    private List<Map<String, Object>> weatherList;

    @Builder
    public SerialDto(boolean isState, boolean isMsgState, Map<String, List<Map<String, String>>> busMap, List<Map<String, Object>> weatherList) {
        this.isState = isState;
        this.isMsgState = isMsgState;
        this.busMap = busMap;
        this.weatherList = weatherList;
    }
}
