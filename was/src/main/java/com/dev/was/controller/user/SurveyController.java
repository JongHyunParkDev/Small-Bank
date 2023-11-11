package com.dev.was.controller.user;

import com.dev.was.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/survey")
public class SurveyController {
    private final SurveyService surveyService;

}
