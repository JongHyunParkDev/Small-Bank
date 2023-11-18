package com.dev.was.controller.anon;

import com.dev.was.dto.SurveyDto;
import com.dev.was.security.AuthenticationUtil;
import com.dev.was.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/anon")
public class AnonSurveyController {
    private final SurveyService surveyService;

    @GetMapping("/survey")
    public SurveyDto getSurveys(@RequestParam(value = "surveyId") Long surveyId) {
        return surveyService.getSurvey(surveyId);
    }
}
