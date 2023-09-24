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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataGoService {
    private final DataConfig busConfig;
    private Map<String, String> busMap;
    private Map<String, String> stationMap;

    @PostConstruct
    public void init(){
        busMap = busConfig.getRoutedBusMap();
        stationMap = busConfig.getStationIdMap();
        refreshWeatherListByStation(LocalDate.now().minusDays(1).format(CommonUtil.LOCAL_DATE_FORMATTER), "2300", null, null);
    }

    public void refreshWeatherListByStation(String baseDate, String baseTime, String nx, String ny) {
        if (nx == null) nx = this.nx;
        if (ny == null) ny = this.ny;
        if (baseDate.equals(lastDateStr) && baseTime.equals(lastTimeStr)) return;

        String urlStr = String.format("https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=%s&&pageNo=1&numOfRows=1000&dataType=JSON&base_date=%s&base_time=%s&nx=%s&ny=%s",
                serviceKey, baseDate, baseTime, nx, ny); /*URL*/

        RestTemplate restTemplate = new RestTemplate();
        URI url = URI.create(urlStr);

        ResponseEntity<WeatherDataVo> responseEntity = restTemplate.getForEntity(url, WeatherDataVo.class);

        logger.info("Weather Response Code {}", responseEntity.getStatusCode().value());
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody().getResponse().getHeader().getResultCode().equals("00")) {
            List<WeatherDataVo.Item> rows = responseEntity.getBody().getResponse().getBody().getItems().getItem();

            WeatherList = createAggregateList(rows);
        }
    }

    private List<Map<String, Object>> createAggregateList(List<WeatherDataVo.Item> list) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        Map<String, Map<String, List<Double>>> result = list.stream()
                .filter(row -> categoryList.contains(row.getCategory()))
                .collect(Collectors.groupingBy(
                        WeatherDataVo.Item::getFcstDate,
                        Collectors.groupingBy(
                                WeatherDataVo.Item::getCategory,
                                Collectors.mapping(
                                        item -> Double.parseDouble(item.getFcstValue()),
                                        Collectors.toList()
                                )
                        )
                ));

        // 결과 출력
        for (Map.Entry<String, Map<String, List<Double>>> dateEntry : result.entrySet()) {
            Map<String, Object> map = new HashMap<>();

            String date = dateEntry.getKey();
            Map<String, List<Double>> categoryData = dateEntry.getValue();

            double tmpMin = categoryData.getOrDefault("TMP", Collections.emptyList())
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .min()
                    .orElse(0.0);

            double tmpMax = categoryData.getOrDefault("TMP", Collections.emptyList())
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .max()
                    .orElse(0.0);

            double snow = categoryData.getOrDefault("SNO", Collections.emptyList())
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .max()
                    .orElse(0.0);

            double rain = categoryData.getOrDefault("PCP", Collections.emptyList())
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .max()
                    .orElse(0.0);

            double sky = categoryData.getOrDefault("SKY", Collections.emptyList())
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);

            map.put("date", date);
            map.put("tmpMax", tmpMax);
            map.put("tmpMin", tmpMin);
            map.put("rain", rain);
            map.put("snow", snow);
            map.put("sky", sky);

            resultList.add(map);
        }

        return resultList;
    }

    // 일단 특정 StationId 를 지정하게 한다.
    public void refreshBusListByStation(String [] stationIds) {
        if (stationIds == null) stationIds = this.stationIds;

        List<Map<String, String>> list = new ArrayList<>();

        for (String stationId : stationIds) {
            String urlStr = String.format("https://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList?serviceKey=%s&stationId=%s",
                    serviceKey, stationId); /*URL*/

            RestTemplate restTemplate = new RestTemplate();
            URI url = URI.create(urlStr);

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            logger.info("Bus Response Code {}", responseEntity.getStatusCode().value());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                list.addAll(getBodyBusList(responseEntity.getBody(), stationId));
            }
        }

        BusList = list;
    }

    // TODO 나중에 또 이런 Data go API 를 쓴다면 여러곳에서 쓸 수 있게 변경한다.
    private List<Map<String, String>> getBodyBusList(String body, String stationId) throws RuntimeException {
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

                map.put("stationName", stationMap.get(stationId));

                list.add(map);
            }
        }

        return list;
    }

    // DB 에 넣지 않고 메모리에 보관한다. (실시간 성 데이터를 보여주기만 하면 되기 때문)
    public static List<Map<String, String>> BusList = new ArrayList<>();

    public static List<Map<String, Object>> WeatherList = new ArrayList<>();

    // cache 용도로 갖고 있는다.
    public static String lastDateStr;

    public static String lastTimeStr;

    private final List<String> categoryList = List.of("TMP", "PCP", "SNO", "SKY");


    @Value("${sppd.data.weather.nx}")
    private String nx;

    @Value("${sppd.data.weather.ny}")
    private String ny;

    @Value("${sppd.data.bus.stationIds}")
    private String[] stationIds;

    @Value("${sppd.data.serviceKey}")
    private String serviceKey;

    private final Logger logger = LoggerFactory.getLogger(DataGoService.class);
}
