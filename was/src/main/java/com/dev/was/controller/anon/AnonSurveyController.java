package com.dev.was.controller.anon;

import com.dev.was.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/anon/survey")
public class AnonSurveyController {
    private final SurveyService surveyService;


}
