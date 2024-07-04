package com.sparta.outsorcingproject.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private LikeTypeEnum type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Like(User user,LikeTypeEnum type, Store store, Review review) {
        this.user = user;
        this.type = type;
        this.store = store;
        this.review = review;
        this.createdAt = LocalDateTime.now();
    }
}
