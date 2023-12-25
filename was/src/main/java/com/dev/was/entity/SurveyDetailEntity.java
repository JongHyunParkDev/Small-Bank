package com.dev.was.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name="tm_survey_detail")
public class SurveyDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private boolean isSort; // true asc, false desc (1,2,3,4), (4,3,2,1)

    @ManyToOne
    @JoinColumn(name = "fk_survey_id")
    private SurveyEntity surveyEntity;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSurveyEntity(SurveyEntity surveyEntity) {
        this.surveyEntity = surveyEntity;
    }

    public void setSort(boolean sort) {
        isSort = sort;
    }

    @Builder
    public SurveyDetailEntity(Long id, String content, String category, boolean isSort, SurveyEntity surveyEntity, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.content = content;
        this.category = category;
        this.isSort = isSort;
        this.surveyEntity = surveyEntity;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
