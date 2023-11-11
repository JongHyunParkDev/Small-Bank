package com.dev.was.repository;


import com.dev.was.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {
    List<SurveyEntity> findAllByUserId(Long userId);
}
