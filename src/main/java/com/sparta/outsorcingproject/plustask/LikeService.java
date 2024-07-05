package com.sparta.outsorcingproject.plustask;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.outsorcingproject.dto.ProfileRequestDto;
import com.sparta.outsorcingproject.dto.ProfileResponseDto;
import com.sparta.outsorcingproject.dto.ReviewResponseDto;
import com.sparta.outsorcingproject.dto.StoreResponseDto;
import com.sparta.outsorcingproject.entity.*;
import com.sparta.outsorcingproject.repository.LikeRepository;
import com.sparta.outsorcingproject.repository.ReviewRepository;
import com.sparta.outsorcingproject.repository.StoreRepository;
import com.sparta.outsorcingproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final LikeDslRepository likeDslRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final MessageSource messageSource;

    @Transactional
    public String createLike(LikeTypeEnum likeType, long typeId, User user) {
        if(likeType == LikeTypeEnum.Store) {
            Store store = storeRepository.findStoreById(typeId, messageSource);

            Like like = likeDslRepository.findByUserIdAndTypeId(user.getId(),likeType,typeId);

            if(like != null) {
                likeDslRepository.delete(like);
                store.subLikeCount();
                return store.getStoreName() + " (스토어) 좋아요를 취소했습니다!";
            }

            if (store.getUser().getId() == user.getId()) {
                return "본인의 가게에 좋아요를 누를 수 없어요!";
            }

            Like newLike = Like.builder()
                    .user(user)
                    .type(likeType)
                    .store(store)
                    .build();
            store.addLikeCount();
            likeDslRepository.save(newLike);

            return store.getStoreName() + " (스토어) 좋아요 완료!";
        }
        else if(likeType == LikeTypeEnum.Review) {
            Review review = reviewRepository.findById(typeId).orElseThrow(()->new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

            Like like = likeDslRepository.findByUserIdAndTypeId(user.getId(),likeType,typeId);

            if(like != null) {
                likeDslRepository.delete(like);
                review.subLikeCount();
                return "(리뷰) 좋아요를 취소했습니다!";
            }

            if (review.getUser().getId() == user.getId()) {
                return "본인의 리뷰에 좋아요를 누를 수 없어요!";
            }

            Like newLike = Like.builder()
                    .user(user)
                    .type(likeType)
                    .review(review)
                    .build();
            review.addLikeCount();
            likeDslRepository.save(newLike);

            return "(리뷰) 좋아요 등록 완료!";
        }

        return "오류 발생";
    }

    public Page<StoreResponseDto> readLikeStore(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by("createAt").ascending());

        Page<Like> likes = likeDslRepository.findAllByUserIdAndTypeId(pageable,user.getId(), LikeTypeEnum.Store);

        return likes.map(like -> new StoreResponseDto(like.getStore()));
    }

    public Page<ReviewResponseDto> readLikeReview(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 5, Sort.by("createAt").ascending());

        //List<Like> likes = likeDslRepository.findAllByUserIdAndTypeId(pageableuser.getId(), LikeTypeEnum.Review);
        Page<Like> likes = likeDslRepository.findAllByUserIdAndTypeId(pageable, user.getId(), LikeTypeEnum.Review);

        return likes.map(like -> new ReviewResponseDto(like.getReview()));
    }


    //프로필 좋아요한 스토어, 리뷰 수 조회 추가  / 유저에 count 넣어서 하는 방법도 있지만 쿼리로 추가해보기
    private final UserRepository userRepository;

    public ResponseEntity<ProfileResponseDto> showProfile(ProfileRequestDto requestDto) {
        String username = requestDto.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 접근입니다."));

        long storeLikeCount = likeDslRepository.findLikeCount(user.getId(),LikeTypeEnum.Store);
        long reviewLikeCount = likeDslRepository.findLikeCount(user.getId(),LikeTypeEnum.Review);

        return ResponseEntity.ok(new ProfileResponseDto(user, storeLikeCount, reviewLikeCount));
    }
}
