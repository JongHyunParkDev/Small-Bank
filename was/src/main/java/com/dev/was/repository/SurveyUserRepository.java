package com.dev.was.repository;

import com.dev.was.entity.SurveyUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyUserRepository extends JpaRepository<SurveyUserEntity, Long> {
}
