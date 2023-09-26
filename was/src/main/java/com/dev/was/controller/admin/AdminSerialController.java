package com.dev.was.controller.admin;

import com.dev.was.controller.ApiException;
import com.dev.was.controller.ExceptionCodeEnum;
import com.dev.was.dto.SerialDto;
import com.dev.was.service.DataGoService;
import com.dev.was.service.SerialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/serial")
@RequiredArgsConstructor
public class AdminSerialController {
    private final SerialService serialService;
    private final DataGoService dataGoService;

    @PostMapping("/send")
    public void send(@RequestBody @Valid RequestSerialSendDto requestSerialSendDto) {
        try {
            serialService.sendSerialMsg(requestSerialSendDto.msg);
        }
        catch (InterruptedException e) {
            throw new ApiException(ExceptionCodeEnum.GENERIC, e.getMessage());
        }
    }

    @GetMapping("/getSerialDto")
    public SerialDto getSerialDto() {
        return createSerialDto();
    }

    private SerialDto createSerialDto() {
        // bus
        dataGoService.refreshBusListByStation(null);
        // weather

        return SerialDto
                .builder()
                .isState(SerialService.isState)
                .isMsgState(SerialService.isMsgState)
                .busMap(DataGoService.BusMap)
                .weatherList(DataGoService.WeatherList)
                .build();
    }

    public static class RequestSerialSendDto {
        @NotBlank
        public String msg;
    }
}
