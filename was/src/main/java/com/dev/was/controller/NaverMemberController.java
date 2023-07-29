package com.dev.was.controller;

import com.dev.was.security.NaverMember;
import com.dev.was.service.NaverMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NaverMemberController {

    @Autowired
    private final NaverMemberService userService;

    @GetMapping("/profile")
    public NaverMember getProfile(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            NaverMember user = userService.getUserInfo(principal);
            return user;
        }
        return null;
    }
}
