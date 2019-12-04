package com.na.backend.repository;

import com.na.backend.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Optional;

public class QuestionRepositoryTest implements CommandLineRunner {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void run(String... args) throws Exception {
        Optional<List<Question>> questions = questionRepository.findByCategoryAndType("father","multi");
        if ( questions.isPresent() ) {
            for ( Question q : questions.get() ) {
                System.out.println(q);
            }
        }
    }
}
