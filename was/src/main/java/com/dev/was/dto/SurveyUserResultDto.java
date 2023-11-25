package com.dev.was.dto;

import com.dev.was.entity.SurveyUserResultEntity;
import lombok.Builder;
import lombok.Data;

@Data
public class SurveyUserResultDto {
    private Long id;
    private String category;
    private Long score;

    @Builder
    public SurveyUserResultDto(Long id, String category, Long score) {
        this.id = id;
        this.category = category;
        this.score = score;
    }

    public SurveyUserResultDto(SurveyUserResultEntity surveyUserResultEntity) {
        this.id = surveyUserResultEntity.getId();
        this.category = surveyUserResultEntity.getCategory();
        this.score = surveyUserResultEntity.getScore();
    }
}
