package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo {
    private String email;
    private String name;

    @Builder
    public UserInfo(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
