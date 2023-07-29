package com.dev.was.service;

import com.dev.was.security.NaverMember;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NaverMemberService {
    private static final String NAVER_USER_INFO_URL = "https://openapi.naver.com/v1/nid/me";

    public NaverMember getUserInfo(OAuth2User oAuth2User) {
        Map<String, Object> userInfo = (Map<String, Object>) oAuth2User.getAttributes().get("response");
        if (userInfo != null) {
            NaverMember user = new NaverMember();
            user.setId((String) userInfo.get("id"));
            user.setName((String) userInfo.get("name"));
            return user;
        }
        return null;
    }

}
