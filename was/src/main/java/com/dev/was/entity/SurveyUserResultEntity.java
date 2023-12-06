package com.dev.was.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name="tm_survey_user_result")
public class SurveyUserResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Long score;

    @ManyToOne
    @JoinColumn(name = "fk_survey_user_id")
    private SurveyUserEntity surveyUserEntity;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public SurveyUserResultEntity(Long id, String category, Long score, SurveyUserEntity surveyUserEntity, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.category = category;
        this.score = score;
        this.surveyUserEntity = surveyUserEntity;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
