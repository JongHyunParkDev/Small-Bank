package com.dev.was.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name="tm_account_category")
public class AccountCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String category;
    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private UserEntity userEntity;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public AccountCategoryEntity(Long id, String category, UserEntity userEntity, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.category = category;
        this.userEntity = userEntity;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
