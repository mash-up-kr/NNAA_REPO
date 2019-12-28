package com.na.backend.service;

import com.na.backend.dto.UserInfo;
import com.na.backend.entity.User;
import com.na.backend.mapper.UserMapper;
import com.na.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserInfo addUser(User user) {
        User newuser = userRepository.save(user);
        System.out.println(newuser);
        return userMapper.toUserInfo(userRepository.save(user));
    }

    public UserInfo findByUserId(Long userId) {
        return userMapper.toUserInfo(userRepository.findByUserId(userId));
    }

    public Boolean isUser(Long userId) {
        User user = userRepository.findByUserId(userId);
        return user != null;
    }

}
