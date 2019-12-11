package com.na.backend.dto;

import com.na.backend.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String email;
    private String password;
    private String token;

    private List<String> bookmark;


    @Builder
    public UserDto(String email, String password, String token){
        this.email = email;
        this.password = password;
        this.token = token;

    }

    @Builder
    public UserDto(String email, List<String> bookmark){
        this.email = email;
        this.bookmark = bookmark;

    }





}
