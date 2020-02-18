package com.na.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LogInDto {
    @ApiModelProperty(example = "user1@abc.com")
    private String email;
    @ApiModelProperty(example = "abc1")
    private String password;

    @Builder
    public LogInDto(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "EmailDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
