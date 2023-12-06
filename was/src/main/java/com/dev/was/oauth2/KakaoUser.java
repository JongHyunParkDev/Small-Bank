package com.dev.was.oauth2;

import java.util.Map;

public class KakaoUser implements OAuthUser {

    public KakaoUser(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.kakaoAccountAttributes = (Map<String, Object>) attributes.get("kakao_account");
        this.profileAttributes = (Map<String, Object>) this.kakaoAccountAttributes.get("profile");
    }

    private Map<String, Object> attributes;
    private Map<String, Object> kakaoAccountAttributes;
    private Map<String, Object> profileAttributes;
    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getName() {
        return profileAttributes.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return kakaoAccountAttributes.get("email").toString();
    }

    @Override
    public String getMobile() {
        return null;
    }

    @Override
    public String getProfileImage() {
        return (String) profileAttributes.get("profile_image_url");
    }
}