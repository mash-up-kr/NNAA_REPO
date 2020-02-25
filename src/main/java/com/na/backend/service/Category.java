package com.na.backend.service;

public enum Category {
    MOTHER("엄마"),
    FATHER("아빠"),
    SISTER("언니/누나"),
    BROTHER("오빠/형"),
    YOUNGER("동생"),
    FRIEND("친구"),
    LOVER("연인");

    String value;

    Category(String value) {
        this.value = value;
    }

    public String value() { return value; }
}