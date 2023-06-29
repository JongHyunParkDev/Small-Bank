package com.dev.pjh.service;

import com.dev.pjh.vo.AptTradingDataVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.*;


@Service
public class TestService {
    @PostConstruct
    public void init() throws IOException {
        test();
    }

    public void test() throws JsonProcessingException {
        System.out.println("test2");
        String urlStr = String.format("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade?serviceKey=%s&LAWD_CD=%s&DEAL_YMD=%s",
                serviceKey, "11110", "201512"); /*URL*/

        RestTemplate restTemplate = new RestTemplate();
        URI url = URI.create(urlStr);
        ResponseEntity<AptTradingDataVo> responseEntity = restTemplate.getForEntity(url, AptTradingDataVo.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println(responseEntity.getBody());
        }
    }

    @Value("${datago.serviceKey}")
    private String serviceKey;

}
