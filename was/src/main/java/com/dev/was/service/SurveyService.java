package com.dev.was.service;

import com.dev.was.dto.AccountDto;
import com.dev.was.dto.SurveyDto;
import com.dev.was.entity.AccountEntity;
import com.dev.was.entity.SurveyEntity;
import com.dev.was.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public List<SurveyDto> getSurveysByUserId(Long userId) {
        List<SurveyEntity> surveyEntityList = surveyRepository.findAllByUserId(userId);

        List<SurveyDto> surveyDtoListDtoList = new ArrayList<>();
        surveyEntityList.forEach(entity -> {
            surveyDtoListDtoList.add(new SurveyDto(entity));
        });

        return surveyDtoListDtoList;
    }
}
