package com.dev.was.dto;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name="tm_user")
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //기본키
    @Column(nullable = false)
    private String userId;
    private String name; //유저 이름
    private String password; //유저 비밀번호
    private String email; //유저 이메일
    private String phone;
    private String birthday;
    private String profileImg;
    private String role; //유저 권한 (일반 유저, 관리자)

    @Builder
    public UserDto(String userId, String name, String password, String email, String phone, String birthday, String profileImg, String role) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.profileImg = profileImg;
        this.role = role;
    }
}