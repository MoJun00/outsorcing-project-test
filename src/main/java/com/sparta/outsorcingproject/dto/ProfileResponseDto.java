package com.sparta.outsorcingproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.outsorcingproject.entity.User;

@NoArgsConstructor
@Getter
public class ProfileResponseDto {
    
    private String username;
    private String introduce;

    public ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.introduce = user.getIntroduce();
    }
}
