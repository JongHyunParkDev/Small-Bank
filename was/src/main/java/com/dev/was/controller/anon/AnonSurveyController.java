package com.dev.was.controller.anon;

import com.dev.was.dto.SurveyDto;
import com.dev.was.service.SurveyService;
import com.dev.was.util.DBUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/anon")
public class AnonSurveyController {
    private final SurveyService surveyService;

    @GetMapping("/survey")
    public SurveyDto getSurveys(@RequestParam(value = "surveyId") Long surveyId) {
        return surveyService.getSurvey(surveyId);
    }

    @PostMapping("/surveyResult")
    public void surveyResult(@RequestBody RequestSurveyUserDto requestSurveyUserDto) {
        surveyService.saveSurveyUserResult(
                requestSurveyUserDto.surveyId,
                requestSurveyUserDto.name,
                DBUtil.toLocalDate(requestSurveyUserDto.birthDay),
                requestSurveyUserDto.gender,
                requestSurveyUserDto.dept,
                requestSurveyUserDto.list
        );
    }

    public static class RequestSurveyUserDto {
        @NotNull
        public Long surveyId;

        @NotBlank
        public String name;
        @NotBlank
        public String birthDay;
        @NotBlank
        public String dept;
        @NotNull
        public Boolean gender;
        @NotNull
        public List<Map<String, Object>> list;
    }
}
