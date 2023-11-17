package com.dev.was.dto;

import com.dev.was.entity.SurveyDetailEntity;
import lombok.Builder;
import lombok.Data;

@Data
public class SurveyDetailDto {
    private Long id;
    private String content;
    private String category;
    private Boolean isSort;

    @Builder
    public SurveyDetailDto(SurveyDetailEntity surveyDetailEntity) {
        this.id = surveyDetailEntity.getId();
        this.content = surveyDetailEntity.getContent();
        this.category = surveyDetailEntity.getCategory();
        this.isSort = surveyDetailEntity.isSort();
    }
}
