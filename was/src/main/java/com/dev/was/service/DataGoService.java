package com.dev.was.service;

import com.dev.was.config.DataConfig;
import com.dev.was.vo.BusDataVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataGoService {
    private final DataConfig busConfig;

    @PostConstruct
    public void init(){
        Map<String, String> map = busConfig.getRoutedBus();
        System.out.println(map);
        getBusListByStation(null);
    }

    // 일단 특정 StationId 를 지정하게 한다.
    public void getBusListByStation(String stationId) {
        if (stationId == null) stationId = this.stationId;
        String urlStr = String.format("https://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList?serviceKey=%s&stationId=%s",
                serviceKey, stationId); /*URL*/

        RestTemplate restTemplate = new RestTemplate();
        URI url = URI.create(urlStr);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String busDataVo = getBodyBustList(responseEntity.getBody());
            logger.info("d");
        }
    }

    private String getBodyBustList(String body) {
        String result = body.substring(body.indexOf("<busArrivalList>"), body.lastIndexOf("</busArrivalList>") + "</busArrivalList>".length());


        return "";
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

    @Value("${sppd.data.serviceKey}")
    private String serviceKey;

    private final Logger logger = LoggerFactory.getLogger(DataGoService.class);
}
