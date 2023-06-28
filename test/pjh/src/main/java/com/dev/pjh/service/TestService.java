package com.dev.pjh.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;


@Service
public class TestService {
    @PostConstruct
    public void init() throws IOException {
        test();
        test2();
        test3();
        test4();
    }

    public void test() throws IOException{
        System.out.println("test");
        StringBuilder urlBuilder = new StringBuilder("http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + "9G4mXvJnJcvcO5RL3kH8WZhCiyCDl00AeqxkvWBoZydbNBK4K%2FBWd1iZcmoI3AYdCu%2B%2BbMpkdrNr%2F5LG8SdPhA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("LAWD_CD","UTF-8") + "=" + URLEncoder.encode("11110", "UTF-8")); /*각 지역별 코드*/
        urlBuilder.append("&" + URLEncoder.encode("DEAL_YMD","UTF-8") + "=" + URLEncoder.encode("201512", "UTF-8")); /*월 단위 신고자료*/
        System.out.println(urlBuilder.toString());
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }

    public void test2() {
        System.out.println("test2");
        String baseUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade";

        String serviceKey = "9G4mXvJnJcvcO5RL3kH8WZhCiyCDl00AeqxkvWBoZydbNBK4K%2FBWd1iZcmoI3AYdCu%2B%2BbMpkdrNr%2F5LG8SdPhA%3D%3D";
        String lawdCd = "11110";
        String dealYmd = "201512";

        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("LAWD_CD", lawdCd)
                .queryParam("DEAL_YMD", dealYmd);

        String url = urlBuilder.toUriString();
        System.out.println(url);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        System.out.println(response);
    }

    public void test3() {
        System.out.println("test3");
        String apiUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade";
        String serviceKey = "9G4mXvJnJcvcO5RL3kH8WZhCiyCDl00AeqxkvWBoZydbNBK4K/BWd1iZcmoI3AYdCu++bMpkdrNr/5LG8SdPhA==";
        String laudCode = "11110";
        String dealYmd = "201512";

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?serviceKey=").append(serviceKey);
        urlBuilder.append("&LAWD_CD=").append(laudCode);
        urlBuilder.append("&DEAL_YMD=").append(dealYmd);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                null,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            System.out.println(responseBody);
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
        }
    }

    public void test4() throws UnsupportedEncodingException {
        System.out.println("test4");
        String apiUrl = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTrade";
//        String serviceKey = "9G4mXvJnJcvcO5RL3kH8WZhCiyCDl00AeqxkvWBoZydbNBK4K/BWd1iZcmoI3AYdCu++bMpkdrNr/5LG8SdPhA==";
        String serviceKey = "9G4mXvJnJcvcO5RL3kH8WZhCiyCDl00AeqxkvWBoZydbNBK4K%2FBWd1iZcmoI3AYdCu%2B%2BbMpkdrNr%2F5LG8SdPhA%3D%3D";
        String laudCode = "11110";
        String dealYmd = "201512";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?serviceKey=").append(serviceKey);
        urlBuilder.append("&LAWD_CD=").append(laudCode);
        urlBuilder.append("&DEAL_YMD=").append(dealYmd);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        System.out.println(urlBuilder.toString());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            System.out.println(responseBody);
        } else {
            System.out.println("Request failed with status code: " + response.getStatusCodeValue());
        }
    }

    @Value("${datago.serviceKey}")
    private String serviceKey;

}
