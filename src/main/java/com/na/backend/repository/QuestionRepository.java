package com.na.backend.repository;

import com.na.backend.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends MongoRepository<Question, String> {

    Optional<List<Question>> findByCategoryAndType(String category, String type);


}
