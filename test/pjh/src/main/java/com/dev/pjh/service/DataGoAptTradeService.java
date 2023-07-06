package com.dev.pjh.service;

import com.dev.pjh.entity.AptRentEntity;
import com.dev.pjh.entity.AptTradeEntity;
import com.dev.pjh.repository.AptRentRepository;
import com.dev.pjh.repository.AptTradeRepository;
import com.dev.pjh.vo.AptRentDataVo;
import com.dev.pjh.vo.AptTradeDataVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.*;
import java.util.List;


// Data.go.kr 에서 받아올 데이터
@Service
@RequiredArgsConstructor
@Slf4j
public class DataGoAptTradeService {
    private final AptTradeRepository aptTradeRepository;

    private final AptRentRepository aptRentRepository;

    @PostConstruct
    public void init() {
        insertDataGoAptTradeInfo("11110", "201512");
        insertDataGoAptRentInfo("11110", "201512");

    }
    //region #매매
    public void insertDataGoAptTradeInfo(String cityCode, String yearMonth) {
        logger.info("insertDataGoAptTradeInfo");
        String urlStr = String.format("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade?serviceKey=%s&LAWD_CD=%s&DEAL_YMD=%s",
                serviceKey, cityCode, yearMonth); /*URL*/

        RestTemplate restTemplate = new RestTemplate();
        URI url = URI.create(urlStr);
        ResponseEntity<AptTradeDataVo> responseEntity = restTemplate.getForEntity(url, AptTradeDataVo.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK
                && responseEntity.getBody().getResponse().getHeader().getResultCode().equals("00")) {

            List<AptTradeDataVo.Item> list = responseEntity.getBody().getResponse().getBody().getItems().getItem();
            logger.info(list.toString());

            list.forEach(item -> {
                aptTradeRepository.save(new AptTradeEntity(item));
            });

        }
    }

    //endregion

    //region #월세 전세
    public void insertDataGoAptRentInfo(String cityCode, String yearMonth) {
        logger.info("insertDataGoAptRentInfo");
        String urlStr = String.format("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent?serviceKey=%s&LAWD_CD=%s&DEAL_YMD=%s&numOfRows=9999",
                serviceKey, cityCode, yearMonth); /*URL*/

        RestTemplate restTemplate = new RestTemplate();
        URI url = URI.create(urlStr);
        ResponseEntity<AptRentDataVo> responseEntity = restTemplate.getForEntity(url, AptRentDataVo.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK
                && responseEntity.getBody().getResponse().getHeader().getResultCode().equals("00")) {

            List<AptRentDataVo.Item> list = responseEntity.getBody().getResponse().getBody().getItems().getItem();
            logger.info(list.toString());

            list.forEach(item -> {
                aptRentRepository.save(new AptRentEntity(item));
            });
        }
    }

    //endregion

    private final String DataGoAptTradeKey = "APT_TRADE";

    private final String DataGoAptRentKey = "APT_RENT";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${datago.serviceKey}")
    private String serviceKey;

}
