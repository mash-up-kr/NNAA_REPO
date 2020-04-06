package com.na.backend.repository;

import com.na.backend.entity.Questionnaire;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionnaireRepository extends MongoRepository<Questionnaire, String> {
    List<Questionnaire> findByCreateUserId(String createUserId);

    List<Questionnaire> findByReceiverId(String receiverId);
}
