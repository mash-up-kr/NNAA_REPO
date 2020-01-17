package com.na.backend.repository;

import com.na.backend.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

    List<Question> findByCategoryAndType(String category, String type);
    List<Question> findByCategory(String category);
    List<Question> findByType(String type);
    List<Question> findQuestionsByIdIn(List<String> ids);
}
