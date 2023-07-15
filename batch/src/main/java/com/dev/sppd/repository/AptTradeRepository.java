package com.dev.sppd.repository;

import com.dev.sppd.entity.AptTradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AptTradeRepository extends JpaRepository<AptTradeEntity, Long> {
}
