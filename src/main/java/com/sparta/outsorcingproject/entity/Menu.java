package com.sparta.outsorcingproject.entity;

import com.sparta.outsorcingproject.dto.MenuRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long price;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Builder
    public Menu(long id, String name, long price, String description,Store store) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.store = store;
    }

    public void update(MenuRequestDto requestDto) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.description = requestDto.getDescription();
    }
}
