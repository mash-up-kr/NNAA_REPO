package com.na.backend.service;

import com.na.backend.dto.NewQuestionDto;
import com.na.backend.dto.QuestionDto;
import com.na.backend.dto.QuestionnaireDto;
import com.na.backend.entity.Question;
import com.na.backend.entity.Questionnaire;
import com.na.backend.entity.User;
import com.na.backend.mapper.QuestionMapper;
import com.na.backend.repository.QuestionRepository;
import com.na.backend.repository.QuestionnaireRepository;
import com.na.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final UserRepository userRepository;
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           QuestionnaireRepository questionnaireRepository,
                           QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionnaireRepository = questionnaireRepository;
        this.userRepository = userRepository;
        this.questionMapper = questionMapper;
    }

    public Question insertNewQuestion(NewQuestionDto newQuestionDto) {
        return questionRepository.insert(questionMapper.toQuestion(newQuestionDto));
    }

    public List<Question> getRecommendationQuestionList(String category, String type) {
        List<Question> result = new ArrayList<>();

        if ( category.equals("all") && type.equals("all") ) {
            result = questionRepository.findAll();
        } else if ( type.equals("all")) {
            result = questionRepository.findByCategory(category);
        } else if ( category.equals("all") ) {
            result = questionRepository.findByType(type);
        } else {
            result = questionRepository.findByCategoryAndType(category, type);
        }

        if(result.isEmpty()){
            // TODO: 결과 없음 custom exception 만들어야 함
            System.out.println("조건에 만족하는 질문이 없음");
            throw new RuntimeException();
        }

        return result;
    }

    public void insertAnswer(QuestionnaireDto questionnaireDto) {

        Questionnaire questionnaire=  Questionnaire.builder()
                .questions(questionnaireDto.getQuestions()).build();

        questionnaireRepository.save(questionnaire);

    }
    

}