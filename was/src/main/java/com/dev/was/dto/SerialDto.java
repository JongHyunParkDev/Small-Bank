package com.dev.was.dto;

import lombok.Builder;
import lombok.Data;


@Data
public class SerialDto {
    private boolean isState;

    private boolean isMsgState;

    @Builder
    public SerialDto(boolean isState, boolean isMsgState) {
        this.isState = isState;
        this.isMsgState = isMsgState;
    }
}
