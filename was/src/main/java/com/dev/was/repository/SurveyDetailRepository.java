package com.dev.was.repository;

import com.dev.was.entity.SurveyDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyDetailRepository extends JpaRepository<SurveyDetailEntity, Long> {
    List<SurveyDetailEntity> findBySurveyEntityId(Long surveyId);
}
