package com.dev.was.service;

import com.dev.was.controller.ApiException;
import com.dev.was.controller.ExceptionCodeEnum;
import com.dev.was.dto.SurveyDetailDto;
import com.dev.was.dto.SurveyDto;
import com.dev.was.entity.SurveyDetailEntity;
import com.dev.was.entity.SurveyEntity;
import com.dev.was.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyDetailRepository surveyDetailRepository;

    public List<SurveyDto> getSurveysByUserId(Long userId) {
        List<SurveyEntity> surveyEntityList = surveyRepository.findAllByUserId(userId);

        List<SurveyDto> surveyDtoListDtoList = new ArrayList<>();
        surveyEntityList.forEach(entity -> {
            surveyDtoListDtoList.add(new SurveyDto(entity));
        });

        return surveyDtoListDtoList;
    }

    public SurveyDto saveSurvey(
            Long id,
            Long userId,
            String title,
            LocalDate startDate,
            LocalDate endDate
    ) {
        SurveyEntity saveSurveyEntity = SurveyEntity.builder()
                .id(id)
                .userId(userId)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        SurveyEntity surveyEntity = surveyRepository.save(saveSurveyEntity);
        return new SurveyDto(surveyEntity);
    }

    public SurveyDto saveSurvey(
            Long id,
            String title,
            LocalDate startDate,
            LocalDate endDate
    ) {
        SurveyEntity surveyEntity = surveyRepository.findById(id)
                .orElseThrow(() -> new ApiException(ExceptionCodeEnum.FOUNT_NOT_ID, "Not Found Survey Id"));

        surveyEntity.setTitle(title);
        surveyEntity.setStartDate(startDate);
        surveyEntity.setEndDate(endDate);

        SurveyEntity resultSurveyEntity = surveyRepository.save(surveyEntity);
        return new SurveyDto(resultSurveyEntity);
    }

    public SurveyDto saveSurvey(
            Long id
    ) {
        SurveyEntity surveyEntity = surveyRepository.findById(id)
                .orElseThrow(() -> new ApiException(ExceptionCodeEnum.FOUNT_NOT_ID, "Not Found Survey Id"));

        surveyEntity.setActive(! surveyEntity.isActive());
        SurveyEntity resultSurveyEntity = surveyRepository.save(surveyEntity);

        return new SurveyDto(resultSurveyEntity);
    }

    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }

    @Transactional
    public SurveyDetailDto saveSurveyDetail(
            Long surveyId,
            String content,
            String category,
            Boolean isSort
    ) {
        Optional<SurveyEntity> surveyEntityOptional = surveyRepository.findById(surveyId);

        if (surveyEntityOptional.isPresent()) {
            SurveyEntity surveyEntity = surveyEntityOptional.get();

            SurveyDetailEntity surveyDetailEntity = SurveyDetailEntity.builder()
                    .content(content)
                    .category(category)
                    .isSort(isSort)
                    .surveyEntity(surveyEntity)
                    .build();

            surveyEntity.getSurveyDetailEntityList().add(surveyDetailEntity);

            surveyRepository.save(surveyEntity);
            return new SurveyDetailDto(surveyDetailEntity);
        }
        else {
            throw new ApiException(ExceptionCodeEnum.DB_ERROR, "Failed to DB insert");
        }
    }
}
