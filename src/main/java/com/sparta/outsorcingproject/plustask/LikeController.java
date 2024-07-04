package com.sparta.outsorcingproject.plustask;

import com.sparta.outsorcingproject.entity.LikeTypeEnum;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
