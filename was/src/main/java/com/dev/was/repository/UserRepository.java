package com.dev.was.repository;

import com.dev.was.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByUserId(String userId);

    public Optional<UserEntity> findByEmail(String email);
}