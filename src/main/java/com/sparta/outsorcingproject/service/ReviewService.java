package com.sparta.outsorcingproject.service;

import com.sparta.outsorcingproject.dto.ReviewRequestDto;
import com.sparta.outsorcingproject.dto.ReviewResponseDto;
import com.sparta.outsorcingproject.entity.Orders;
import com.sparta.outsorcingproject.entity.Review;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.repository.OrdersRepository;
import com.sparta.outsorcingproject.repository.ReviewRepository;
import com.sparta.outsorcingproject.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    MessageSource messageSource;


    //리뷰 등록
    public ResponseEntity<String> addReview(Long ordersId, ReviewRequestDto requestDto, User user) {

        //ordersId 로 오더 찾아오기 -> 없다면 예외 발생시키기
        Orders orders = ordersRepository.findById(ordersId).orElseThrow(() ->
                new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.find.orders",
                                null,
                                Locale.getDefault()
                        ))
        );

        // 1의 유저와 2의 유저 같은지 비교하기
        if (!orders.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 요청한 주문이 아닙니다.");
        }

        // 3.리뷰가 중복인이 체그하기
        boolean isReviewExists = reviewRepository.existsByOrders(orders);
        if (isReviewExists) {
            throw new IllegalArgumentException("이미 이 주문에 대한 리뷰를 작성하였습니다.");
        }

        Review review = new Review(user, orders, orders.getStore(), requestDto.getReview(), requestDto.getRate());
        reviewRepository.save(review);
        return ResponseEntity.ok("리뷰 작성 완료");
    }


    //리뷰 수정
    @Transactional
    public ResponseEntity<String> updateReview(Long reviewId, ReviewRequestDto requestDto, User user) {

        //reviewId 로 리뷰 찾아오기 -> 없다면 예외 발생시키기
        Review review = getReviewBYId(reviewId);

        //  유저가 같은지 비교하기
        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 작성한 리뷰가 아닙니다.");
        }

        review.updateReview(requestDto.getReview(), requestDto.getRate());
        return ResponseEntity.ok("리뷰 수정 완료");
    }


    // 2.reviewId 로 리뷰 찾아오기 -> 없다면 예외 발생시키기
    public ResponseEntity<ReviewResponseDto> getReview(Long reviewId) {

        //존재하는 리뷰인지
        Review review = getReviewBYId(reviewId);

        ReviewResponseDto responseDto = new ReviewResponseDto(review);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    //상점 리뷰 전체 조회
    public ResponseEntity<List<ReviewResponseDto>> getStoreReviews(Long storeId) {

        storeRepository.findStoreById(storeId, messageSource);

        List<Review> reviews = reviewRepository.findAllByStore_Id(storeId);

        //리뷰가 존재하는지 체크
        if (reviews.size() == 0) {
            throw new IllegalArgumentException(
                    messageSource.getMessage(
                            "not.find,review",
                            null,
                            Locale.getDefault()
                    )
            );
        }

        List<ReviewResponseDto> reviewList = reviews.stream().map(ReviewResponseDto::new).toList();
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }


    //리뷰 삭제
    public ResponseEntity<String> deleteReview(Long reviewId, User user) {

        Review review = getReviewBYId(reviewId);

        //본인이 작성한 리뷰인지 확인하기
        if (!review.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 작성한 리뷰가 아닙니다.");
        }

        reviewRepository.deleteById(reviewId);
        return ResponseEntity.ok("리뷰 삭제 완료");
    }


    private Review getReviewBYId(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new IllegalArgumentException(
                        messageSource.getMessage(
                                "not.find.review",
                                null,
                                Locale.getDefault()
                        ))
        );
        return review;
    }


}
