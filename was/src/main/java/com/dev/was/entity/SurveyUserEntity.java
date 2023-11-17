package com.dev.was.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name="tm_survey_user")
public class SurveyUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본키

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDay;

    @Column(nullable = false)
    private boolean gender;

    @Column(nullable = false)
    private String dept;

    @ManyToOne
    @JoinColumn(name = "fk_survey_id")
    private SurveyEntity surveyEntity;

    @BatchSize(size = 100)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SurveyUserResultEntity> surveyUserResultEntityList;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public SurveyUserEntity(Long id, String name, LocalDate birthDay, boolean gender, String dept, SurveyEntity surveyEntity, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.dept = dept;
        this.surveyEntity = surveyEntity;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
