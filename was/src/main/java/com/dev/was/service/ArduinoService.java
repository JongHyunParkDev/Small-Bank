package com.dev.was.service;

import com.dev.was.config.DataConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ArduinoService {
    private final DataConfig busConfig;

    @PostConstruct
    public void init(){
        Map<String, String> map = busConfig.getRoutedBus();
        System.out.println(map);
    }



    // DB 에 넣지 않고 메모리에 보관한다. (실시간 성 데이터를 보여주기만 하면 되기 때문)
    private static String weather = "";

    private static String bus = "";

    @Value("${sppd.data.weather.nx}")
    private String nx;

    @Value("${sppd.data.weather.ny}")
    private String ny;

    @Value("${sppd.data.bus.stationId}")
    private String stationId;
}
