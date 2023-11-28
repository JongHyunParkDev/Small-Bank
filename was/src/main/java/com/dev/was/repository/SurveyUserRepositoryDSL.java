package com.dev.was.repository;

import static com.dev.was.entity.QSurveyUserEntity.surveyUserEntity;

import com.dev.was.entity.SurveyUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SurveyUserRepositoryDSL {
    private final JPAQueryFactory queryFactory;

    public SurveyUserRepositoryDSL(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<SurveyUserEntity> findSurveyUserEntityList(Long surveyId, String name, String dept, Boolean gender) {
        BooleanBuilder where = new BooleanBuilder();

        where.and(surveyUserEntity.surveyEntity.id.eq(surveyId));

        if (name != null && !name.isEmpty())
            where.and(surveyUserEntity.name.contains(name));
        if (dept != null && !dept.isEmpty())
            where.and(surveyUserEntity.dept.contains(dept));
        if (gender != null)
            where.and(surveyUserEntity.gender.eq(gender));

        return queryFactory
                .selectFrom(surveyUserEntity)
                .where(where)
                .fetch();
    }
}
