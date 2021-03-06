package com.na.backend.service;

import com.na.backend.dto.QuestionResponseDto;
import com.na.backend.dto.QuestionnaireQuestionDto;
import com.na.backend.entity.Question;
import com.na.backend.entity.User;
import com.na.backend.mapper.QuestionMapper;
import com.na.backend.repository.QuestionRepository;
import com.na.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final UserRepository userRepository;

    public QuestionService(QuestionRepository questionRepository,
                           QuestionMapper questionMapper,
                           UserRepository userRepository) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.userRepository = userRepository;
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
    public List<QuestionResponseDto> getRandomQuestions(String myId, String category, Integer size) {
        User me = userRepository.findById(myId).get();
        List<String> questionIds = new ArrayList<>();

        questionRepository.getQuestionIdsByCategory(category)
                .forEach(question -> questionIds.add(question.getId()));

        Set<String> selectedQuestionIds = new HashSet<>();
        Random random = new Random();
        int idsLength = questionIds.size();

        size = (idsLength < size) ? idsLength : size; // db에 있는 최대개수를 넘지 않도록

        while (selectedQuestionIds.size() < size) {
            selectedQuestionIds.add(questionIds.get(random.nextInt(idsLength)));
        }
        List<Question> selectedQuestions = questionRepository.findQuestionsByIdIn(new ArrayList(selectedQuestionIds));

        return questionMapper.toQuestionResponseDtoList(selectedQuestions, me.getBookmarks());
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
}