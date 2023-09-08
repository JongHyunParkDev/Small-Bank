package com.dev.was.controller.admin;

import com.dev.was.controller.ApiException;
import com.dev.was.controller.ExceptionCodeEnum;
import com.dev.was.service.SerialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/serial")
@RequiredArgsConstructor
public class AdminSerialController {
    private final SerialService serialService;

    @PostMapping("/send")
    public void send(@RequestBody @Valid RequestSerialSendDto requestSerialSendDto) {
        try {
            serialService.sendSerialMsg(requestSerialSendDto.msg);
        }
        catch (InterruptedException e) {
            throw new ApiException(ExceptionCodeEnum.GENERIC, e.getMessage());
        }
    }

    public static class RequestSerialSendDto {
        @NotBlank
        public String msg;
    }
}
