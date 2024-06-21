package com.sparta.outsorcingproject.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequestDto {
    private String review;

    @NotNull
    @Min(value = 1, message = "최소 선택가능 점수는 1점입니다.")
    @Max(value = 5, message = "쵀대 선택가능 점수는 5점입니다.")
    private Double rate;

}


