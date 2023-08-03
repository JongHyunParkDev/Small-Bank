package com.dev.was.repository;

import com.dev.was.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByUserIdAndDatetimeBetween(String userId,LocalDate startDate, LocalDate endDate);
}
