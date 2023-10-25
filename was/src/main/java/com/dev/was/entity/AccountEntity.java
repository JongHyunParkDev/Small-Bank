package com.dev.was.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name="tm_account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본키
    @Column(nullable = false)
    private Long userId; // userEntity id
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private String memo;
    @Column(nullable = false)
    private Long money;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String type;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public AccountEntity(Long id, Long userId, LocalDate date, String time, String memo, Long money, String category, String type, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.time = time;
        this.memo = memo;
        this.money = money;
        this.category = category;
        this.type = type;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
