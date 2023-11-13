package com.dev.was.controller.user;

import com.dev.was.dto.AccountDto;
import com.dev.was.dto.SurveyDto;
import com.dev.was.security.AuthenticationUtil;
import com.dev.was.service.SurveyService;
import com.dev.was.util.DBUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/surveys")
    public List<SurveyDto> getSurveys() {
        Long id = AuthenticationUtil.getCurrentId();
        return surveyService.getSurveysByUserId(id);
    }

    @PostMapping("/survey")
    public SurveyDto saveSurvey(@RequestBody @Valid RequestAddSurveyDto requestAddSurveyDto) {
        Long userId = AuthenticationUtil.getCurrentId();

        return surveyService.saveSurvey(null, userId, requestAddSurveyDto.title,
                DBUtil.toLocalDate(requestAddSurveyDto.startDate), DBUtil.toLocalDate(requestAddSurveyDto.endDate));
    }

    @PutMapping("/survey")
    public SurveyDto updateSurvey(@RequestBody @Valid RequestUpdateSurveyDto requestUpdateSurveyDto) {

        return surveyService.saveSurvey(requestUpdateSurveyDto.id, requestUpdateSurveyDto.title,
                DBUtil.toLocalDate(requestUpdateSurveyDto.startDate), DBUtil.toLocalDate(requestUpdateSurveyDto.endDate));
    }

    @PutMapping("/surveyIsActive")
    public SurveyDto updateSurvey(@RequestBody @Valid RequestSurveyDto requestSurveyDto) {
        return surveyService.saveSurvey(requestSurveyDto.id);
    }

    @DeleteMapping("/survey")
    public void deleteSurvey(@RequestBody @Valid RequestSurveyDto requestSurveyDto) {
        surveyService.deleteSurvey(requestSurveyDto.id);
    }


    public static class RequestAddSurveyDto {
        @NotNull
        public String title;
        @NotBlank
        public String startDate;
        @NotBlank
        public String endDate;
    }


    public static class RequestUpdateSurveyDto {
        @NotNull
        public Long id;
        @NotNull
        public String title;
        @NotBlank
        public String startDate;
        @NotBlank
        public String endDate;
    }

    public static class RequestSurveyDto {
        @NotNull
        public Long id;
    }
}
