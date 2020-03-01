package com.na.backend.service;

import com.na.backend.dto.NewQuestionDto;
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
import java.util.Random;

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

    public List<Question> getRandomQuestions(String category, Integer size) {
        List<String> questionIds = new ArrayList<>();

        questionRepository.getQuestionIdsByCategory(category)
                .forEach( question -> questionIds.add(question.getId()));

        List<String> selectedQuestionIds = new ArrayList<>();

        Random random = new Random();
        Integer idsLength = questionIds.size();
        for( int i = 0; i < size; i++ ) {
            selectedQuestionIds.add(questionIds.get(random.nextInt(idsLength)));
        }

        return questionRepository.findQuestionsByIdIn(selectedQuestionIds);
    }

    // TODO: dto 생성시에 체크할 수 있도록 util 로 빼기
    public Boolean isInvalidCategory(String category) {
        if (category == null) return true;

        for ( Category c : Category.values() ){
            if (category.equals(c.value())) {
                return false;
            }
        }

        return true;
    }

    // TODO: dto 생성시에 체크할 수 있도록 util 로 빼기
    public Boolean isInvalidType(String type) {
        if (type == null) return true;

        for ( Type t : Type.values() ){
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

    @Transactional
    public List<Question> getBookmarkList(String myId) {

        // 유저를 토큰으로 먼저 찾는다
        //UserEntity user = userRepository.findByToken(token);
        User user = new User();
        List<String> questionIds = user.getBookmarks();

        return questionRepository.findQuestionsByIdIn(questionIds);
    }

    public void addBookmark(String myId, String questionId) {

        User user = new User();
        //UserEntity user = userRepository.findByToken(token);

        List<String> bookmarks = user.getBookmarks();
        // 그 배열에 질문을 추가한다
        bookmarks.add(questionId);
        //유저정보 업데이트
        //userRepository.save(user);
    }

    public void removeBookmark(String myId, String questionId) {

        // 유저를 토큰으로먼저 찾는다
        User user = new User();
        //UserEntity user = userRepository.findByToken(token);
        // 그 유저의 즐찾 배열을 찾는다
        List<String> bookmarks = user.getBookmarks();
        // 그 배열에 질문을 삭제
        bookmarks.remove(questionId);
        //유저정보 업데이트
        //userRepository.save(user);
    }
}