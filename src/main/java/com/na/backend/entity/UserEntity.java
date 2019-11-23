package com.na.backend.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Table(name = "user")
@NoArgsConstructor
@Getter
@Entity
public class UserEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String token;

    @Builder
    public UserEntity(String email, String password, String token){
        this.email = email;
        this.password = password;
        this.token = token;

    }

}
