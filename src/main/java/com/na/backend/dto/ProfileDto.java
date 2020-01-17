package com.na.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {
    @ApiModelProperty(example = "찌루루")
    private String nickname;

    @Builder
    public ProfileDto(String nickname) {
        this.nickname = nickname;
    }
}
