package com.sparta.outsorcingproject.dto;

import lombok.Getter;

@Getter
public class ProfileRequestDto {
    private String username;

    public ProfileRequestDto(String username) {
        this.username = username;
    }
}