package com.sparta.outsorcingproject.controller;

import com.sparta.outsorcingproject.dto.ReviewRequestDto;
import com.sparta.outsorcingproject.dto.ReviewResponseDto;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import com.sparta.outsorcingproject.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    //리뷰 등록
    //인증 인가 완성 되면 UserDetails 에서 뽑아와야함
    @PostMapping("/{ordersId}")
    public ResponseEntity<String> addReview(@Valid @PathVariable Long ordersId, @RequestBody ReviewRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails ) {
    return  reviewService.addReview(ordersId,requestDto,userDetails.getUser());
    }


    // 리뷰 수정
    //인증 인가 완성 되면 UserDetails 에서 뽑아와야함
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody ReviewRequestDto requestDto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails)
    {return reviewService.updateReview(reviewId, requestDto,userDetails.getUser());
    }

    // 단일 리뷰 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long reviewId) {
        return reviewService.getReview(reviewId);
    }

    //상점 리뷰 조회
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReviewResponseDto>> getStoreReviews(@PathVariable Long storeId) {
        return reviewService.getStoreReviews(storeId);
    }


    // 리뷰 삭제
    //인증 인가 완성 되면 UserDetails 에서 뽑아와야함
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reviewService.deleteReview(reviewId, userDetails.getUser());
    }

}



