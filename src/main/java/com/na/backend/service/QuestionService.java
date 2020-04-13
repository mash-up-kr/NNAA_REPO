package com.na.backend.service;

import com.na.backend.dto.QuestionnaireQuestionDto;
import com.na.backend.entity.Question;
import com.na.backend.mapper.QuestionMapper;
import com.na.backend.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuestionService(QuestionRepository questionRepository,
                           QuestionMapper questionMapper) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public List<Question> getRecommendationQuestionList(String category, String type) {
        List<Question> result;

        if (category.equals("all") && type.equals("all")) {
            result = questionRepository.findAll();
        } else if (type.equals("all")) {
            result = questionRepository.findByCategory(category);
        } else if (category.equals("all")) {
            result = questionRepository.findByType(type);
        } else {
            result = questionRepository.findByCategoryAndType(category, type);
        }

        if (result.isEmpty()) {
            // TODO: 결과 없음 custom exception 만들어야 함
            System.out.println("조건에 만족하는 질문이 없음");
            throw new RuntimeException();
        }

        return result;
    }

    // 위 getRecommendationQuestionList 랑 합쳐야함. category 입력안했을 때의 처리도 할 수 있게
    public List<Question> getRandomQuestions(String category, Integer size) {
        List<String> questionIds = new ArrayList<>();

        questionRepository.getQuestionIdsByCategory(category)
                .forEach(question -> questionIds.add(question.getId()));

        List<String> selectedQuestionIds = new ArrayList<>();

        Random random = new Random();
        int idsLength = questionIds.size();
        for (int i = 0; i < size; i++) {
            selectedQuestionIds.add(questionIds.get(random.nextInt(idsLength)));
        }

        return questionRepository.findQuestionsByIdIn(selectedQuestionIds);
    }

    // TODO: dto 생성시에 체크할 수 있도록 util 로 빼기
    public Boolean isInvalidCategory(String category) {
        if (category == null) return true;

        for (Category c : Category.values()) {
            if (category.equals(c.value())) {
                return false;
            }
        }

        return true;
    }

    // TODO: dto 생성시에 체크할 수 있도록 util 로 빼기
    public Boolean isInvalidType(String type) {
        if (type == null) return true;

        for (Type t : Type.values()) {
            if (type.equals(t.value())) {
                return false;
            }
        }

        return true;
    }

    // TODO: dto 생성시에 체크할 수 있도록 util 로 빼기
    public Boolean isInvalidQuestionId(String questionId) {
        if (questionId == null) return true;

        return !questionRepository.findById(questionId).isPresent();
    }


    public Question insertNewQuestion(QuestionnaireQuestionDto questionDto) {
        return questionRepository.insert(questionMapper.toQuestion(questionDto));
    }

}