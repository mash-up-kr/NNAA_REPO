package com.na.backend.repository;

import com.na.backend.dto.NewQuestionDto;
import com.na.backend.entity.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

    Optional<List<Question>> findByCategoryAndType(String category, String type);
    Question insert(NewQuestionDto newQuestionDto);

    Optional<List<Question>> findByIds(List<String> ids);

    Optional<Question> findById(String id);

}
