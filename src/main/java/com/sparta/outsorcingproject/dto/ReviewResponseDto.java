package com.sparta.outsorcingproject.dto;

import com.sparta.outsorcingproject.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private Long id;
    private String review;
    private Double rating;
    private String storeName;
    private long likeCount;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.review = review.getReview();
        this.rating = review.getRate();
        this.storeName = review.getStore().getStoreName();
        this.likeCount = review.getLikeCount();
    }
}
