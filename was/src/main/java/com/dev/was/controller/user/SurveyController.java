package com.dev.was.controller.user;

import com.dev.was.dto.SurveyDetailDto;
import com.dev.was.dto.SurveyDto;
import com.dev.was.dto.SurveyUserDto;
import com.dev.was.dto.SurveyUserResultDto;
import com.dev.was.security.AuthenticationUtil;
import com.dev.was.service.SurveyService;
import com.dev.was.util.DBUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/surveyDetails")
    public List<SurveyDetailDto> getSurveyDetails(@RequestParam(value = "surveyId") Long surveyId) {
        return surveyService.getSurveyDetailsBySurveyId(surveyId);
    }

    @PostMapping("/surveyDetail")
    public SurveyDetailDto saveSurveyDetail(@RequestBody @Valid RequestAddSurveyDetailDto requestAddSurveyDetailDto) {
        return surveyService.saveSurveyDetail(requestAddSurveyDetailDto.surveyId, requestAddSurveyDetailDto.content,
                requestAddSurveyDetailDto.category, requestAddSurveyDetailDto.isSort);
    }

    @PutMapping("/surveyDetail")
    public SurveyDetailDto updateSurveyDetail(@RequestBody @Valid RequestUpdateSurveyDetailDto requestUpdateSurveyDetailDto) {
        return surveyService.updateSurveyDetail(requestUpdateSurveyDetailDto.surveyDetailId,
                requestUpdateSurveyDetailDto.content, requestUpdateSurveyDetailDto.content,
                requestUpdateSurveyDetailDto.isSort);
    }

    @DeleteMapping("/surveyDetail")
    public void deleteSurveyDetail(@RequestBody @Valid RequestDetailSurveyDetailDto requestDetailSurveyDetailDto) {
        surveyService.deleteSurveyDetail(requestDetailSurveyDetailDto.surveyDetailId);
    }

    public static class RequestAddSurveyDetailDto {
        @NotNull
        public Long surveyId;
        @NotBlank
        public String content;

        @NotBlank
        public String category;
        @NotNull
        public Boolean isSort;
    }

    public static class RequestUpdateSurveyDetailDto {
        @NotNull
        public Long surveyDetailId;
        @NotBlank
        public String content;

        @NotBlank
        public String category;
        @NotNull
        public Boolean isSort;
    }

    public static class RequestDetailSurveyDetailDto {
        @NotNull
        public Long surveyDetailId;
    }

    @GetMapping("/surveyUsers")
    public Page<SurveyUserDto> getSurveyUsers(
            @RequestParam(value = "surveyId") Long surveyId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "dept") String dept,
            @RequestParam(value = "gender", required = false) Boolean gender,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return surveyService.getSurveyUser(surveyId, name, dept, gender, pageable);
    }
}
