package com.na.backend.service;


import com.na.backend.dto.UserDto;
import com.na.backend.entity.UserEntity;
import com.na.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;


    @Transactional
    public String save(UserDto userDto){

        return userRepository.save(userDto.toEntity()).getToken();
    }

}
