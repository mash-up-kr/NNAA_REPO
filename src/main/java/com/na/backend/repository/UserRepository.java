package com.na.backend.repository;

import com.na.backend.dto.UserDto;
import com.na.backend.entity.Question;
import com.na.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {


    //유저 찾기
    public UserEntity findByToken(String token);


}
