package com.dev.was.repository;

import com.dev.was.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findById(Long id);
    public UserEntity findByUserId(String userId);

    public UserEntity findByEmail(String email);

    public Boolean existsByEmail(String email);

    public UserEntity findByPhone(String phone);
}