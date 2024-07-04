package com.sparta.outsorcingproject.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Review extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private String review;
    private Double rate;

    @ColumnDefault("0")
    private Long likeCount;


    public Review(User user, Orders orders, Store store, String review, Double rate) {
        this.user = user;
        this.orders = orders;
        this.store = store;
        this.review = review;
        this.rate = rate;
    }

    public void updateReview(String review, Double rate) {
        this.review = review;
        this.rate = rate;
    }

    // 좋아요 갱신
    public void addLikeCount(){
        this.likeCount++;
    }

    public void subLikeCount(){
        this.likeCount--;
    }
}
