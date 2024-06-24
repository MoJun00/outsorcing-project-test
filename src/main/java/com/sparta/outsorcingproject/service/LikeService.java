package com.sparta.outsorcingproject.service;

import com.sparta.outsorcingproject.entity.Like;
import com.sparta.outsorcingproject.entity.Store;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.repository.LikeRepository;
import com.sparta.outsorcingproject.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final StoreRepository storeRepository;
    private final MessageSource messageSource;

    @Transactional
    public ResponseEntity<String> updateLike(User user, Long storeId) {
        Store store = storeRepository.findStoreById(storeId, messageSource);

        boolean check = likeRepository.existsByUserAndStore(user,store);

        if(check){
            likeRepository.delete(likeRepository.findByUser(user));
            store.subtractCount();
        }else{
            Like like = new Like(user, store);
            likeRepository.save(like);
            store.addCount();
        }

        return ResponseEntity.ok("좋아요 갱신 완료");
    }
}
