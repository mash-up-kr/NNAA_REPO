package com.na.backend.repository;

import com.na.backend.entity.Questionnaire;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QustionnairRepository extends MongoRepository<Questionnaire, String> {




}