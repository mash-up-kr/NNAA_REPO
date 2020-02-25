package com.na.backend.mapper;

import com.na.backend.dto.InboxQuestionnaireDto;
import com.na.backend.dto.UserAuthDto;
import com.na.backend.dto.UserInfoDto;
import com.na.backend.entity.Questionnaire;
import com.na.backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public List<UserInfoDto> toUserInfoDtos(List<User> users){
        List<UserInfoDto> userInfoDtos = new ArrayList<>();

        users.forEach(user ->
                userInfoDtos.add(UserInfoDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .build())
        );

        return userInfoDtos;
    }

    public UserAuthDto toUserAuthDto(User user) {

        return UserAuthDto.builder()
                .id(user.getId())
                .token(user.getToken())
                .name(user.getName())
                .build();
    }

}
