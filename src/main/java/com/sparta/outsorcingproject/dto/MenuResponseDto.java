package com.sparta.outsorcingproject.dto;

import com.sparta.outsorcingproject.entity.Menu;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuResponseDto {
    private long id;

    private String name;

    private long price;

    private String description;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
    }
}
