package com.dev.was.oauth2;

import java.util.Map;

public class NaverUser implements OAuthUser{

    public NaverUser(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    private Map<String, Object> attributes;
    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getMobile() {
        return (String) attributes.get("mobile");
    }

    @Override
    public String getProfileImage() {
        return (String) attributes.get("profile_image");
    }
}