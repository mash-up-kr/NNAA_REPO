package com.na.backend.dto;

import com.na.backend.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String email;
    private String password;
    private String token;


    @Builder
    public UserDto(String email, String password, String token){
        this.email = email;
        this.password = password;
        this.token = token;

    }



    public UserEntity toEntity(){
        return UserEntity.builder()

                .email(email)
                .password(password)
                .token(token)
                .build();
    }
}
