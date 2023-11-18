package com.dev.was.dto;

import com.dev.was.entity.SurveyEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class SurveyDto {
    private Long id;
    private Long userId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;

    private List<SurveyDetailDto> detailDtoList;

    @Builder
    public SurveyDto(SurveyEntity surveyEntity) {
        this.id = surveyEntity.getId();
        this.userId = surveyEntity.getUserId();
        this.title = surveyEntity.getTitle();
        this.startDate = surveyEntity.getStartDate();
        this.endDate = surveyEntity.getEndDate();
        this.isActive = surveyEntity.isActive();
    }
}
