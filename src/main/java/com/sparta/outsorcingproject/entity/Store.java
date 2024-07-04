package com.sparta.outsorcingproject.entity;

import com.sparta.outsorcingproject.dto.StoreRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "store")
public class Store extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String sellerName;

    @Column(nullable = false)
    private String storeIntroducing;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Column
    private Long likeCount;

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> Menus = new ArrayList<>();

    @OneToMany(mappedBy = "store",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    
    // 생성
    public Store(User user, StoreRequestDto requestDto) {
        this.user = user;
        this.sellerName = user.getUsername();
        this.storeName = requestDto.getStoreName();
        this.storeIntroducing = requestDto.getStoreIntroducing();
        this.likeCount = 0L;
    }

    // 수정
    public void update(StoreRequestDto requestDto) {
        this.storeName = requestDto.getStoreName();
        this.storeIntroducing = requestDto.getStoreIntroducing();
    }

    // 좋아요 갱신
    public void addLikeCount(){
        this.likeCount++;
    }

    public void subLikeCount(){
        this.likeCount--;
    }
}