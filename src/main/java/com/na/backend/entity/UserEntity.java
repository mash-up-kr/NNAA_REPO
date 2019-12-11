package com.na.backend.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;


@Document(collection = "user")
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {



    @Id
    private String id;


    private String email;


    private String password;


    private String token;


    private List<String> bookmark;

    @Builder
    public UserEntity(String email, String password, String token){
        this.email = email;
        this.password = password;
        this.token = token;

    }

    @Builder
    public UserEntity(String email, List<String> bookmark){

        this.email = email;
        this.bookmark= bookmark;

    }


}
