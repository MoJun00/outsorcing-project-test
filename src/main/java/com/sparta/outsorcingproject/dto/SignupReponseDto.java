package com.sparta.outsorcingproject.dto;

import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class SignupReponseDto {
    private String username;
    private String introduce;
    private UserRoleEnum role;

    public SignupReponseDto(User user){
        this.username = user.getUsername();
        this.introduce = user.getIntroduce();
        this.role = user.getRole();
    }
}
