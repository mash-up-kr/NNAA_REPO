package com.na.backend.repository;

import com.na.backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    //User save(User user);
    User findByUserId(Long userId);
}
