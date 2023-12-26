package com.dev.was.repository;

import com.dev.was.entity.AccountCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountCategoryRepository extends JpaRepository<AccountCategoryEntity, Long> {
    List<AccountCategoryEntity> findByUserEntityId(Long userId);
}
