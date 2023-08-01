package com.dev.was.repository;

import com.dev.was.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<UserDto, Long> {
    public Optional<UserDto> findByUserId(String name);
}