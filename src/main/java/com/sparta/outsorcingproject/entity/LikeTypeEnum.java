package com.sparta.outsorcingproject.entity;

public enum LikeTypeEnum {
    Store(LikeType.Store),
    Review(LikeType.Review),;

    private final String type;

    LikeTypeEnum(String type) {
        this.type = type;
    }

    public static class LikeType{
        public static final String Store = "Store";
        public static final String Review = "Review";
    }
}
