package com.na.backend.repository;

import com.na.backend.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

    @Query(value = "{category : ?0}", fields = "{ '_id': 1 }")
    List<Question> getQuestionIdsByCategory(String category);

    List<Question> findByCategoryAndType(String category, String type);

    List<Question> findByCategory(String category);

    List<Question> findByType(String type);

    List<Question> findQuestionsByIdIn(List<String> ids);

}
