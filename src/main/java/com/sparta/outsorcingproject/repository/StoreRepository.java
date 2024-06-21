package com.sparta.outsorcingproject.repository;

import com.sparta.outsorcingproject.entity.Store;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Locale;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    /** 존재하지 않는 가게 **/
    default Store findStoreById(Long id, MessageSource messageSource) {
        return findById(id).orElseThrow(()->new IllegalArgumentException(messageSource.getMessage("not.find.store",null, Locale.getDefault())));
    }

    /** 존재하지 않는 가게 + 유저가 가게의 소유주가 아닌 경우 오류 **/
    default Store findStoreById(Long storeId,Long userId, MessageSource messageSource) {
        Store store = findStoreById(storeId, messageSource);
        if(store.getUser().getId() != userId)
            throw new IllegalArgumentException(messageSource.getMessage("mismatch.user",null, Locale.getDefault()));
        return store;
    }
}