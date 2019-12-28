package com.na.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class User {
    private Long userId;
    private String email;
    private Boolean emailVerified;
    private String name;
    private String provider;

    @Builder
    public User(Long userId, String email, Boolean emailVerified, String name, String provider) {
        this.userId = userId;
        this.email = email;
        this.emailVerified = emailVerified;
        this.name = name;
        this.provider = provider;
    }
}
