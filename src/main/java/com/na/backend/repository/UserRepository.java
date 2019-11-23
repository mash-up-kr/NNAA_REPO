package com.na.backend.repository;

import com.na.backend.dto.UserDto;
import com.na.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

}
