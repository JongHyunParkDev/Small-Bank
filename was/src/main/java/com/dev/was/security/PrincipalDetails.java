package com.dev.was.security;

import com.dev.was.dto.UserDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalDetails implements OAuth2User {
    private UserDto userDto;
    private Map<String, Object> attributes;

    public PrincipalDetails(UserDto userDto) {
        this.userDto = userDto;
    }

    public PrincipalDetails(UserDto userDto, Map<String, Object> attributes) {
        this.userDto = userDto;
        this.attributes = attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDto.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getName() {
        return "name";
    }
}