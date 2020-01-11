package com.na.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocialDto {

    @ApiModelProperty(example = "kakao")
    private String provider;
    @ApiModelProperty(example = "z_2bBQcwWuRB6EFboouf1OsKWyMtrIMaEc8_nQo9dycAAAFvlTqYrw")
    private String accessToken;

    @Builder
    public SocialDto(String provider, String accessToken) {
        this.provider = provider;
        this.accessToken = accessToken;
    }
}
