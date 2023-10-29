package com.dev.was.security;

import com.dev.was.entity.UserEntity;
import com.dev.was.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2MemberService extends DefaultOAuth2UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User = " + oAuth2User.getAttributes());
        String provider = userRequest.getClientRegistration().getClientId();
        Map<String, String> map = (Map<String, String>) oAuth2User.getAttributes().get("response");
        String providerId = map.get("id");
        String userId = provider + "::" + providerId; //중복이 발생하지 않도록 provider와 providerId를 조합
        String name = map.get("name");
        String phone = map.get("mobile");
        String birthday = map.get("birthday");
        String email = map.get("email");
        String profileImg = map.get("profile_image");
        String role = "ROLE_USER"; //일반 유저
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) { //찾지 못했다면
            userEntity = UserEntity.builder()
                    .userId(userId)
                    .name(name)
                    .email(email)
                    .password(encoder.encode("password"))
                    .phone(phone)
                    .birthday(birthday)
                    .profileImg(profileImg)
                    .role(role)
                    .build();
            userRepository.save(userEntity);
        }
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
