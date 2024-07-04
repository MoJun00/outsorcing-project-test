package com.sparta.outsorcingproject.plustask;

import com.sparta.outsorcingproject.entity.*;
import com.sparta.outsorcingproject.repository.LikeRepository;
import com.sparta.outsorcingproject.repository.OrdersRepository;
import com.sparta.outsorcingproject.repository.ReviewRepository;
import com.sparta.outsorcingproject.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

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

        return null;
    }
}
