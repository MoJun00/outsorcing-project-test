package com.sparta.outsorcingproject.dto;

import com.sparta.outsorcingproject.entity.Store;
import lombok.Getter;

@Getter
public class StoreResponseDto {
    private String storeName;
    private String storeIntroducing;
    private String sellerName;

    public StoreResponseDto(Store store) {
        this.storeName = store.getStoreName();
        this.storeIntroducing = store.getStoreIntroducing();
        this.sellerName = store.getSellerName();
    }
}
