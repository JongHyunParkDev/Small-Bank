package com.dev.pjh.repository;

import com.dev.pjh.entity.AptRentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AptRentRepository extends JpaRepository<AptRentEntity, Long> {
}
