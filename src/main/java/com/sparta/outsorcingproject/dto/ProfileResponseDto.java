package com.sparta.outsorcingproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.outsorcingproject.entity.User;

@NoArgsConstructor
@Getter
public class ProfileResponseDto {
    
    private String username;
    private String introduce;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String profileUrl;

    public ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.introduce = user.getIntroduce();
        this.profileUrl = user.getProfileUrl();
    }
}
