package com.dev.was.dto;

import com.dev.was.entity.UserEntity;
import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private String phone;
    private String birthday;
    private String profileImg;
    private String role;

    public UserDto(UserEntity userEntity) {
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.phone = userEntity.getPhone();
        this.birthday = userEntity.getBirthday();
        this.profileImg = userEntity.getProfileImg();
        this.role = userEntity.getRole();
    }
}
