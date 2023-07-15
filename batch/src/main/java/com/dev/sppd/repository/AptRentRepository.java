package com.dev.sppd.repository;

import com.dev.sppd.entity.AptRentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AptRentRepository extends JpaRepository<AptRentEntity, Long> {
}
