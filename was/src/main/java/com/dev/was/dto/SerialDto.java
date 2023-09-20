package com.dev.was.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;


@Data
public class SerialDto {
    private boolean isState;
    private boolean isMsgState;
    private List<Map<String, String>> busList;
    private List<Map<String, Object>> weatherList;

    @Builder
    public SerialDto(boolean isState, boolean isMsgState, List<Map<String, String>> busList, List<Map<String, Object>> weatherList) {
        this.isState = isState;
        this.isMsgState = isMsgState;
        this.busList = busList;
        this.weatherList = weatherList;
    }
}
