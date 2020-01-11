package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailDto {

    private String email;
    private String password;

    @Builder
    public EmailDto(String email, String password){
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
