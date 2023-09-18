package com.dev.was.service;

import com.dev.was.config.DataConfig;
import com.dev.was.util.CommonUtil;
import com.dev.was.vo.WeatherDataVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DataGoService {
    private final DataConfig busConfig;
    private Map<String, String> busMap;

    @PostConstruct
    public void init(){
        busMap = busConfig.getRoutedBus();
        refreshWeatherListByStation(LocalDate.now().minusDays(1).format(CommonUtil.LOCAL_DATE_FORMATTER), "2300", null, null);
    }

    public void refreshWeatherListByStation(String baseDate, String baseTime, String nx, String ny) {
        if (nx == null) nx = this.nx;
        if (ny == null) ny = this.ny;
        if (baseDate.equals(lastDateStr) && baseTime.equals(lastTimeStr)) return;

        List<Map<String, String>> list = new ArrayList<>();

        String urlStr = String.format("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=%s&&pageNo=1&numOfRows=1000&dataType=JSON&base_date=%s&base_time=%s&nx=%s&ny=%s",
                serviceKey, baseDate, baseTime, nx, ny); /*URL*/

        RestTemplate restTemplate = new RestTemplate();
        URI url = URI.create(urlStr);

        ResponseEntity<WeatherDataVo> responseEntity = restTemplate.getForEntity(url, WeatherDataVo.class);

        logger.info("Weather Response Code {}", responseEntity.getStatusCode().value());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            List<WeatherDataVo.Item> rows = responseEntity.getBody().getResponse().getBody().getItems().getItem();
            for (WeatherDataVo.Item row : rows) {
                if (row.getCategory().equals("POP")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("date", row.getFcstDate());
                    map.put("time", row.getFcstTime());
                    map.put("value", row.getFcstValue());
                    list.add(map);
                }
            }
        }
        WeatherList = list;
    }

    // 일단 특정 StationId 를 지정하게 한다.
    public void refreshBusListByStation(String stationId) {
        if (stationId == null) stationId = this.stationId;

        List<Map<String, String>> list = Collections.emptyList();

        String urlStr = String.format("https://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList?serviceKey=%s&stationId=%s",
                serviceKey, stationId); /*URL*/

        RestTemplate restTemplate = new RestTemplate();
        URI url = URI.create(urlStr);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        logger.info("Bus Response Code {}", responseEntity.getStatusCode().value());
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            list = getBodyBusList(responseEntity.getBody());
        }

        BusList = list;
    }

    // TODO 나중에 또 이런 Data go API 를 쓴다면 여러곳에서 쓸 수 있게 변경한다.
    private List<Map<String, String>> getBodyBusList(String body) throws RuntimeException {
        List<Map<String, String>> list = new ArrayList<>();
        // 필요한 것은 몇 번 버스가 해당 정류장에 몇 분 후 에 오는지가 궁금함으로 time, routeId 만 가져온다.
        List<String> findColList = List.of("predictTime1", "predictTime2", "routeId");

        String separator = "</busArrivalList>";
        if (body.contains(separator)) {
            String result = body.substring(body.indexOf("<busArrivalList>"), body.lastIndexOf(separator) + separator.length());
            String arr[] = result.split(separator);

            for (String el : arr) {
                Map<String, String> map = new HashMap<>();

                findColList.forEach(col -> {
                    String s = "<" + col + ">";
                    String e = "</" + col + ">";
                    String findStr = el.substring(el.indexOf(s) + s.length(), el.lastIndexOf(e));

                    if (col.equals("routeId")) {
                        findStr = busMap.get(findStr);
                    }

                    if (! findStr.equals("")) map.put(col, findStr);
                });

                list.add(map);
            }
        }

        return list;
    }

    // DB 에 넣지 않고 메모리에 보관한다. (실시간 성 데이터를 보여주기만 하면 되기 때문)
    public static List<Map<String, String>> BusList = new ArrayList<>();

    public static List<Map<String, String>> WeatherList = new ArrayList<>();

    // cache 용도로 갖고 있는다.
    public static String lastDateStr;

    public static String lastTimeStr;

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
