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

    @Builder
    public SurveyUserDto(Long id, String name, LocalDate birthDay, boolean gender, String dept, List<SurveyUserResultDto> surveyUserResultDtoList) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.dept = dept;
        this.surveyUserResultDtoList = surveyUserResultDtoList;
    }

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
