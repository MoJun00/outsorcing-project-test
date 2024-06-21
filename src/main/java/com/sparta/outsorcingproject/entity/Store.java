package com.sparta.outsorcingproject.entity;

import com.sparta.outsorcingproject.dto.StoreRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String sellerName;

    @Column(nullable = false)
    private String storeIntroducing;


    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> Menus = new ArrayList<>();

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Store(User user, StoreRequestDto requestDto) {
        this.user = user;
        this.sellerName = user.getUsername();
        this.storeName = requestDto.getStoreName();
        this.storeIntroducing = requestDto.getStoreIntroducing();
    }


    public void update(StoreRequestDto requestDto) {
        this.storeName = requestDto.getStoreName();
        this.storeIntroducing = requestDto.getStoreIntroducing();
    }
}
