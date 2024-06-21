package com.sparta.outsorcingproject.repository;

import com.sparta.outsorcingproject.entity.Store;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Locale;

public interface StoreRepository extends JpaRepository<Store, Long> {

    default Store findStoreById(Long id, MessageSource messageSource) {
        return findById(id).orElseThrow(()->new IllegalArgumentException(messageSource.getMessage("not.find.store",null, Locale.getDefault())));
    }
    /*default Store findStoreById(Long storeId,Long userId, MessageSource messageSource) {
        Store store = findById(storeId).orElseThrow(()->new IllegalArgumentException(messageSource.getMessage("not.find.store",null, Locale.getDefault())));
        if(store.getUser().getId() != userId)
            new IllegalArgumentException("해당 유저가 소유한 가게가 아닙니다.");
        return store;
    }*/
}