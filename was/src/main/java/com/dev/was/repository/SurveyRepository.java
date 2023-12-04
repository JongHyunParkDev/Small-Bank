package com.dev.was.repository;


import com.dev.was.entity.SurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<SurveyEntity, Long> {

    Optional<SurveyEntity> findById(Long id);

//    @Query("SELECT s FROM tm_survey s WHERE s.id = :id AND s.startDate <= current_date AND s.endDate >= current_date AND s.isActive = true")
//    Optional<SurveyEntity> findActiveSurveysById(@Param("id") Long id);

    List<SurveyEntity> findAllByUserId(Long userId);
}
