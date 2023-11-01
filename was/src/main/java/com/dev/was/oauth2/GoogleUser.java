package com.dev.was.oauth2;

import java.util.Map;

public class GoogleUser implements OAuthUser{

    public GoogleUser(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    private Map<String, Object> attributes;
    @Override
    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getProvider() {
        return "google";
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
        return null;
    }

    @Override
    public String getProfileImage() {
        return (String) attributes.get("picture");
    }
}