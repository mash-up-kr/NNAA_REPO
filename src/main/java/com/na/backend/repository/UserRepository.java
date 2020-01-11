package com.na.backend.repository;

import com.na.backend.entity.Question;
import com.na.backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<String> findFriendsById(String myId);
    Optional<User> findByUid(String uid);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
}
