package com.na.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@NoArgsConstructor
@Getter
public class User {
    @Id
    private String id;
    private Long uid;
    private String email;
    private Boolean emailVerified;
    private String password;
    private String name;
    private String provider;
    private String token;
    private List<String> bookmarks;

    @Builder
    public User(String id,
                Long uid,
                String email,
                Boolean emailVerified,
                String password,
                String name,
                String provider,
                String token,
                List<String> bookmarks) {
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.emailVerified = emailVerified;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.token = token;
        this.bookmarks = bookmarks;
    }
}
