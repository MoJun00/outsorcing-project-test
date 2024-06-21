package com.sparta.outsorcingproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StoreRequestDto {

    @NotBlank(message = "가게 이름을 입력해주세요.")
    private String storeName;

    @NotBlank(message = "가게 설명을 입력해주세요.")
    private String storeIntroducing;
}
