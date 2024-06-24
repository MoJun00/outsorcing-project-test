package com.sparta.outsorcingproject.repository;

import com.sparta.outsorcingproject.entity.Like;
import com.sparta.outsorcingproject.entity.Store;
import com.sparta.outsorcingproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

    // 게시글은 현재 존재 하는 상태 (Store store에서 검증함)
    // => 좋아요 요청하는 User가 해당 게시글에 좋아요를 눌렀는지, 안눌렀는지 확인하기 위해 사용
    boolean existsByUserAndStore(User user, Store store);

    Like findByUser(User user);
}
