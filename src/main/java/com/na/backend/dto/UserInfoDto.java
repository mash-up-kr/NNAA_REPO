package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private String id;
    private String email;
    private String name;

    @Builder
    public UserInfoDto(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
