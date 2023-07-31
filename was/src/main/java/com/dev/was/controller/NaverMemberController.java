package com.dev.was.controller;

import com.dev.was.service.OAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NaverMemberController {
    private final OAuth2MemberService oAuth2MemberService;


}
