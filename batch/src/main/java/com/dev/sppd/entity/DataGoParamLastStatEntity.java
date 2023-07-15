package com.dev.sppd.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tm_data_go_lstat")
public class DataGoParamLastStatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String configId;

    // JSON 형태 저장
    @Column(nullable = false, columnDefinition = "TEXT")
    private String config;
}
