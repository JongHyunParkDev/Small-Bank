package com.dev.was.dto;


import com.dev.was.entity.SurveyUserEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SurveyUserDto {
    private Long id;
    private String name;
    private LocalDate birthDay;
    private boolean gender;
    private String dept;
    private List<SurveyUserResultDto> surveyUserResultDtoList;

    public SurveyUserDto(SurveyUserEntity surveyUserEntity) {
        this.id = surveyUserEntity.getId();
        this.name = surveyUserEntity.getName();
        this.birthDay = surveyUserEntity.getBirthDay();
        this.gender = surveyUserEntity.isGender();
        this.dept = surveyUserEntity.getDept();
        this.surveyUserResultDtoList = surveyUserEntity.getSurveyUserResultEntityList()
                .stream()
                .map(SurveyUserResultDto::new)
                .toList();
    }
}
