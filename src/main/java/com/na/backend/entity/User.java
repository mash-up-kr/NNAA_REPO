package com.na.backend.entity;

import com.na.backend.dto.BookmarkQuestionDto;
import com.na.backend.dto.UserInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String uid;
    private String email;
    private String salt;
    private String password;
    private String name;
    private String provider;
    private String token;
    private Map<String, BookmarkQuestionDto> bookmarks;
    private List<UserInfoDto> friends;

    @Builder
    public User(String id,
                String uid,
                String email,
                String password,
                String salt,
                String name,
                String provider,
                String token,
                Map<String, BookmarkQuestionDto> bookmarks,
                List<UserInfoDto> friends) {
        this.id = id;
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.provider = provider;
        this.token = token;
        this.name = name;
        this.bookmarks = bookmarks;
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", provider='" + provider + '\'' +
                ", token='" + token + '\'' +
                ", bookmarks=" + bookmarks +
                ", friends=" + friends +
                '}';
    }
}
