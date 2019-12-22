package com.na.backend.repository;

import com.na.backend.entity.Heart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartRepository extends MongoRepository<Heart, String> {
    List<String> findQuestionIdsById(String userId);
}
