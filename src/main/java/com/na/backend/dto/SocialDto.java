package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocialDto {

    private String provider;
    private String accessToken;

    @Builder
    public SocialDto(String provider, String accessToken) {
        this.provider = provider;
        this.accessToken = accessToken;
    }
}
