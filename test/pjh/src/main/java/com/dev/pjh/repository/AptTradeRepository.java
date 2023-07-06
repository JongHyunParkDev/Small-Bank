package com.dev.pjh.repository;

import com.dev.pjh.entity.AptTradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AptTradeRepository extends JpaRepository<AptTradeEntity, Long> {
}
