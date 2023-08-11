package com.dev.was.security;

import com.dev.was.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements OAuth2User {
    private UserEntity userEntity;
    private Map<String, Object> attributes;

    public PrincipalDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public PrincipalDetails(UserEntity userEntity, Map<String, Object> attributes) {
        this.userEntity = userEntity;
        this.attributes = attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getName() {
        return userEntity.getUserId();
    }
}