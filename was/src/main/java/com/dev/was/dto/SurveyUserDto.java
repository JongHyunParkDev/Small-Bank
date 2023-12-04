package com.dev.was.dto;


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
}
