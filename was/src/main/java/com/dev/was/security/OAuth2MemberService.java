package com.dev.was.security;

import com.dev.was.controller.ExceptionCodeEnum;
import com.dev.was.entity.UserEntity;
import com.dev.was.oauth2.GoogleUser;
import com.dev.was.oauth2.KakaoUser;
import com.dev.was.oauth2.OAuthUser;
import com.dev.was.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuthUser oauthUser = null;

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if (registrationId.equals("google"))
            oauthUser = new GoogleUser(oAuth2User.getAttributes());
        else if (registrationId.equals("kakao"))
            oauthUser = new KakaoUser(oAuth2User.getAttributes());
        else
            throw new CustomAuthenticationException(ExceptionCodeEnum.LOGIN_ERROR, "OAUTH ERROR");

        String userId = oauthUser.getProvider() + "::" + oauthUser.getProviderId();
        String name = oauthUser.getName();
        String phone = oauthUser.getMobile();
        String email = oauthUser.getEmail();
        String profileImg = oauthUser.getProfileImage();
        String role = "ROLE_USER"; // 일반 유저
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) { // 찾지 못했다면
            try {
                userEntity = UserEntity.builder()
                        .userId(userId)
                        .name(name)
                        .email(email)
                        .password(encoder.encode("password"))
                        .phone(phone)
                        .profileImg(profileImg)
                        .role(role)
                        .build();
                userRepository.save(userEntity);
            }
            catch (Exception e) {
                throw new CustomAuthenticationException(ExceptionCodeEnum.OAUTH_LOGIN_ERROR);
            }
        }
        else if (! profileImg.equals(userEntity.getProfileImg())) {
            userEntity.setProfileImg(profileImg);
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
