package com.dev.was.service;

import com.dev.was.controller.ApiException;
import com.dev.was.controller.ExceptionCodeEnum;
import com.dev.was.dto.SurveyDetailDto;
import com.dev.was.dto.SurveyDto;
import com.dev.was.dto.SurveyUserDto;
import com.dev.was.dto.SurveyUserResultDto;
import com.dev.was.entity.SurveyDetailEntity;
import com.dev.was.entity.SurveyEntity;
import com.dev.was.entity.SurveyUserEntity;
import com.dev.was.entity.SurveyUserResultEntity;
import com.dev.was.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;
    private final SurveyDetailRepository surveyDetailRepository;
    private final SurveyUserRepository surveyUserRepository;

    private final SurveyUserRepositoryDSL surveyUserRepositoryDSL;

    public List<SurveyDto> getSurveysByUserId(Long userId) {
        List<SurveyEntity> surveyEntityList = surveyRepository.findAllByUserId(userId);

        List<SurveyDto> surveyDtoListDtoList = new ArrayList<>();
        surveyEntityList.forEach(entity -> {
            surveyDtoListDtoList.add(new SurveyDto(entity));
        });

        return surveyDtoListDtoList;
    }

    public SurveyDto getSurvey(Long surveyId) {
        SurveyEntity surveyEntity = surveyRepository.findById(surveyId).orElseThrow(
                () -> new ApiException(ExceptionCodeEnum.DB_ERROR, "Not found survey id"));

        LocalDate now = LocalDate.now();
        if (! surveyEntity.isActive())
            throw new ApiException(ExceptionCodeEnum.UNAVAILABLE_DATA, "Survey state is not active");
        else if (now.compareTo(surveyEntity.getStartDate()) < 0 || now.compareTo(surveyEntity.getEndDate()) > 0 )
            throw new ApiException(ExceptionCodeEnum.UNAVAILABLE_DATA, "Survey date is not available");
        else if (surveyEntity.getSurveyDetailEntityList().isEmpty())
            throw new ApiException(ExceptionCodeEnum.UNAVAILABLE_DATA, "Survey Detail is empty");

        SurveyDto surveyDto = new SurveyDto(surveyEntity);
        surveyDto.setSurveyDetailDtoList(surveyEntity
                .getSurveyDetailEntityList()
                .stream()
                .map(SurveyDetailDto::new)
                .collect(Collectors.toList())
                );

        return surveyDto;
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
        SurveyEntity saveSurveyEntity = surveyRepository.findById(id)
                .orElseThrow(() -> new ApiException(ExceptionCodeEnum.DB_ERROR, "Not Found Survey Id"));

        saveSurveyEntity.setTitle(title);
        saveSurveyEntity.setStartDate(startDate);
        saveSurveyEntity.setEndDate(endDate);

        SurveyEntity surveyEntity = surveyRepository.save(saveSurveyEntity);
        return new SurveyDto(surveyEntity);
    }

    @Transactional
    public SurveyDto saveSurvey(
            Long id
    ) {
        SurveyEntity surveyEntity = surveyRepository.findById(id)
                .orElseThrow(() -> new ApiException(ExceptionCodeEnum.DB_ERROR, "Not Found Survey Id"));

        // 비활성화 시 result 삭제
        if (surveyEntity.isActive()) {
            surveyEntity.getSurveyUserEntityList().clear();
        }
        surveyEntity.setActive(! surveyEntity.isActive());

        SurveyEntity resultSurveyEntity = surveyRepository.save(surveyEntity);

        return new SurveyDto(resultSurveyEntity);
    }

    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }

    @Transactional
    public List<SurveyDetailDto> getSurveyDetailsBySurveyId(
            Long surveyId
    ) {
        Optional<SurveyEntity> surveyEntityOptional = surveyRepository.findById(surveyId);

        if (surveyEntityOptional.isPresent()) {
            SurveyEntity surveyEntity = surveyEntityOptional.get();

            return surveyEntity.getSurveyDetailEntityList()
                    .stream()
                    .map(SurveyDetailDto::new)
                    .collect(Collectors.toList());
        }
        else {
            throw new ApiException(ExceptionCodeEnum.DB_ERROR, "Failed to DB insert");
        }
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

            surveyDetailEntity.setSurveyEntity(surveyEntity);

            surveyDetailEntity = surveyDetailRepository.save(surveyDetailEntity);

//            surveyEntity.getSurveyDetailEntityList().add(surveyDetailEntity);
//
//            surveyRepository.save(surveyEntity);
            return new SurveyDetailDto(surveyDetailEntity);
        }
        else {
            throw new ApiException(ExceptionCodeEnum.DB_ERROR, "Failed to DB insert");
        }
    }

    public SurveyDetailDto updateSurveyDetail(
            Long surveyDetailId,
            String content,
            String category,
            Boolean isSort
    ) {
        Optional<SurveyDetailEntity> surveyEntityDeOptional = surveyDetailRepository.findById(surveyDetailId);

        if (surveyEntityDeOptional.isPresent()) {
            SurveyDetailEntity surveyDetailEntity = surveyEntityDeOptional.get();

            surveyDetailEntity.setContent(content);
            surveyDetailEntity.setCategory(category);
            surveyDetailEntity.setSort(isSort);

            surveyDetailRepository.save(surveyDetailEntity);
            return new SurveyDetailDto(surveyDetailEntity);
        }
        else {
            throw new ApiException(ExceptionCodeEnum.DB_ERROR, "Failed to DB update");
        }
    }

    public void deleteSurveyDetail(Long surveyDetailId) {
        surveyDetailRepository.deleteById(surveyDetailId);
    }

    public void saveSurveyUserResult(Long surveyId, String name, LocalDate birthDay, Boolean gender, String dept,
                                     List<Map<String, Object>> list) {
        SurveyEntity surveyEntity = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ApiException(ExceptionCodeEnum.DB_ERROR, "Not Found Survey Id"));

        SurveyUserEntity surveyUserEntity = SurveyUserEntity.builder()
                .name(name)
                .birthDay(birthDay)
                .gender(gender)
                .dept(dept)
                .surveyEntity(surveyEntity)
                .build();

        List<SurveyUserResultEntity> surveyUserResultEntityList = new ArrayList<>();
        list.forEach(map -> {
            surveyUserResultEntityList.add(SurveyUserResultEntity.builder()
                    .category((String) map.get("category"))
                    .score(((Integer)map.get("value")).longValue())
                    .surveyUserEntity(surveyUserEntity)
                    .build());
        });

        surveyUserEntity.setSurveyUserResultEntityList(surveyUserResultEntityList);
        surveyEntity.getSurveyUserEntityList().add(surveyUserEntity);
        surveyRepository.save(surveyEntity);
    }

    public Page<SurveyUserDto> getSurveyUser(Long surveyId, String name, String dept, Boolean gender, Pageable pageable) {
        List<SurveyUserDto> surveyUserDtoList = new ArrayList<>();

        Page<SurveyUserEntity> surveyUserEntityList = surveyUserRepositoryDSL.findSurveyUserEntityList(surveyId,
                name, dept, gender, pageable);

        surveyUserEntityList.forEach(surveyUserEntity -> {
            List<SurveyUserResultDto> surveyUserResultDtoList = new ArrayList<>();

            surveyUserEntity.getSurveyUserResultEntityList().forEach(surveyUserResultEntity ->
                    surveyUserResultDtoList.add(new SurveyUserResultDto(surveyUserResultEntity)));

            surveyUserDtoList.add(
                    SurveyUserDto.builder()
                            .id(surveyUserEntity.getId())
                            .name(surveyUserEntity.getName())
                            .dept(surveyUserEntity.getDept())
                            .gender(surveyUserEntity.isGender())
                            .birthDay(surveyUserEntity.getBirthDay())
                            .surveyUserResultDtoList(surveyUserResultDtoList)
                            .build()
            );
        });

        return new PageImpl<>(surveyUserDtoList, pageable, surveyUserEntityList.getTotalElements());
    }
}
