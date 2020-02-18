package com.na.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpDto {
    @ApiModelProperty(example = "user1@abc.com")
    private String email;
    @ApiModelProperty(example = "abc1")
    private String password;
    @ApiModelProperty(example = "찌루루")
    private String name;

    @Builder
    public SignUpDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
