package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAuthDto {
    private String id;
    private String token;
    private String name;

    @Builder
    public UserAuthDto(String id, String token, String name) {
        this.id = id;
        this.token = token;
        this.name = name;
    }
}
