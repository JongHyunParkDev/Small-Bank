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

    @Builder
    public SerialDto(boolean isState, boolean isMsgState, List<Map<String, String>> busList) {
        this.isState = isState;
        this.isMsgState = isMsgState;
        this.busList = busList;
    }
}
