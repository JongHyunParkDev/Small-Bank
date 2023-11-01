package com.dev.was.oauth2;

public interface OAuthUser {
    String getProviderId();
    String getProvider();
    String getName();
    String getEmail();

    String getMobile();

    String getProfileImage();
}
