package com.dev.was.repository;


import com.dev.was.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {

    Optional<SurveyEntity> findById(Long id);
    List<SurveyEntity> findAllByUserId(Long userId);
}
