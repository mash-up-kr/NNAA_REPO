package com.na.backend.mapper;

import com.na.backend.dto.UserInfo;
import com.na.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserInfo toUserInfo(User user) {
        return UserInfo.builder()
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
