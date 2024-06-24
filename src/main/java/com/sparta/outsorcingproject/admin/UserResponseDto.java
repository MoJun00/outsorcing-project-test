package com.sparta.outsorcingproject.admin;

import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private UserRoleEnum role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
