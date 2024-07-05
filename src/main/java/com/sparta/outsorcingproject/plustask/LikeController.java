package com.sparta.outsorcingproject.plustask;

import com.sparta.outsorcingproject.dto.ProfileRequestDto;
import com.sparta.outsorcingproject.dto.ProfileResponseDto;
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

    //스토어 좋아요 누르기
    @PostMapping("/store/{storeId}")
    public ResponseEntity<String> createLike(@PathVariable("storeId") long storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String response = likeService.createLike(LikeTypeEnum.Store, storeId,userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //리뷰 좋아요
    @PostMapping("/review/{reviewId}")
    public ResponseEntity<String> createLike2(@PathVariable("reviewId") long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String response = likeService.createLike(LikeTypeEnum.Review, reviewId,userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //좋아요 스토어 페이징
    @PostMapping("/store/read/{pageNumber}")
    public ResponseEntity<Page<StoreResponseDto>> readLikeStore(@PathVariable int pageNumber, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<StoreResponseDto> response = likeService.readLikeStore(pageNumber, userDetails.getUser());

        return ResponseEntity.ok().body(response);
    }

    //좋아요 리뷰 페이징
    @PostMapping("/review/read/{pageNumber}")
    public ResponseEntity<Page<ReviewResponseDto>> readLikeReview(@PathVariable int pageNumber, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<ReviewResponseDto> response = likeService.readLikeReview(pageNumber, userDetails.getUser());

        return ResponseEntity.ok().body(response);
    }

    //프로필 조회
    //https://localhost:443/like/profile/get?username=winner7780
    @GetMapping("/profile/get")
    public ResponseEntity<ProfileResponseDto> getProfile(@RequestParam String username) {
        ProfileRequestDto requestDto = new ProfileRequestDto(username);
        return likeService.showProfile(requestDto);
    }
}
