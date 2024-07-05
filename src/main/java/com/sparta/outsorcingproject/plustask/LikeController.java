package com.sparta.outsorcingproject.plustask;

import com.sparta.outsorcingproject.dto.ReviewResponseDto;
import com.sparta.outsorcingproject.dto.StoreResponseDto;
import com.sparta.outsorcingproject.entity.Like;
import com.sparta.outsorcingproject.entity.LikeTypeEnum;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<String> createLike(@PathVariable("storeId") long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String response = likeService.createLike(LikeTypeEnum.Store, storeId,userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/review/{reviewId}")
    public ResponseEntity<String> createLike2(@PathVariable("reviewId") long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String response = likeService.createLike(LikeTypeEnum.Review, reviewId,userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/store/read/{pageNumber}")
    public ResponseEntity<Page<StoreResponseDto>> readLikeStore(@PathVariable int pageNumber, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<StoreResponseDto> response = likeService.readLikeStore(pageNumber, userDetails.getUser());

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/review/read/{pageNumber}")
    public ResponseEntity<Page<ReviewResponseDto>> readLikeReview(@PathVariable int pageNumber, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<ReviewResponseDto> response = likeService.readLikeReview(pageNumber, userDetails.getUser());

        return ResponseEntity.ok().body(response);
    }
}
