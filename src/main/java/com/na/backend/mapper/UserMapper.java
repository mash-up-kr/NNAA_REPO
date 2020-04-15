package com.na.backend.mapper;

import com.na.backend.dto.BookmarkQuestionDto;
import com.na.backend.dto.UserAuthDto;
import com.na.backend.dto.UserInfoDto;
import com.na.backend.entity.Question;
import com.na.backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserMapper {

    public List<UserInfoDto> toUserInfoDtos(List<User> users) {
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

    public List<Question> toQuestions(Map<String, BookmarkQuestionDto> bookmarkQuestionDtos) {
        List<Question> bookmarkQuestions = new ArrayList<>();
        for(String key: bookmarkQuestionDtos.keySet()) {
            BookmarkQuestionDto bookmarkQuestionDto = bookmarkQuestionDtos.get(key);
            bookmarkQuestions.add(Question.builder()
                    .id(key)
                    .content(bookmarkQuestionDto.getContent())
                    .category(bookmarkQuestionDto.getCategory())
                    .type(bookmarkQuestionDto.getType())
                    .choices(bookmarkQuestionDto.getChoices())
                    .build());
        }

        return bookmarkQuestions;
    }

}
