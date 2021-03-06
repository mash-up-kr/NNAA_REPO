package com.na.backend.service;

import com.na.backend.dto.*;
import com.na.backend.entity.Questionnaire;
import com.na.backend.entity.User;
import com.na.backend.exception.EntityNotFoundException;
import com.na.backend.exception.InvalidException;
import com.na.backend.mapper.QuestionMapper;
import com.na.backend.repository.QuestionnaireRepository;
import com.na.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;
    private final UserRepository userRepository;
    private final QuestionMapper questionMapper;

    public QuestionnaireService(QuestionnaireRepository questionnaireRepository,
                                UserRepository userRepository,
                                QuestionMapper questionMapper) {
        this.questionnaireRepository = questionnaireRepository;
        this.userRepository = userRepository;
        this.questionMapper = questionMapper;
    }

    public boolean isReceiver(String myId, String questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new EntityNotFoundException("invalid questionnaire id"));

        return myId.equals(questionnaire.getReceiverId());
    }

    public QuestionnaireResponseDto createQuestionnaire(String createUserId, QuestionnaireDto questionnaireDto) {
        User createUser = userRepository.findById(createUserId)
                .orElseThrow(() -> new EntityNotFoundException("no user for create user id (" + createUserId + ")"));

        String receiverId = questionnaireDto.getReceiverId();
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new EntityNotFoundException("no user for receiver id (" + receiverId + ")"));

        Questionnaire newQuestionnaire = questionnaireRepository.insert(questionMapper.toQuestionnaireWhenNew(createUser, receiver, questionnaireDto));

        // 북마크에 있는 질문인지 체크
        return questionMapper.toQuestionnaireResponseDto(newQuestionnaire, createUser.getBookmarks());
    }

    @Transactional
    public QuestionnaireResponseDto insertAnswer(String myId, String questionnaireId, AnswerQuestionnaireDto answerQuestionnaireDto) {
        User me = userRepository.findById(myId).get();

        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new EntityNotFoundException("no questionnaire for id (" + questionnaireId + ")"));

        // 요청자가 답변자와 일치하는지 확인
        if (myId.equals(questionnaire.getReceiverId())) {
            Questionnaire answeredQuestionnaire = questionnaireRepository.save(fillQuestionnaire(questionnaire, answerQuestionnaireDto));
            return questionMapper.toQuestionnaireResponseDto(answeredQuestionnaire, me.getBookmarks());
        } else {
            throw new InvalidException("The Questionnaire(questionnaire id:"+ questionnaireId +") is not for this user(user id:" + myId + ")!");
        }
    }

    public List<OutboxQuestionnaireDto> getOutboxQuestionnaires(String myId) {

        return questionMapper.toOutboxQuestionnaireDtos(questionnaireRepository.findByCreateUserId(myId));
    }

    public List<InboxQuestionnaireDto> getInboxQuestionnaires(String myId) {

        return questionMapper.toInboxQuestionnaireDtos(questionnaireRepository.findByReceiverId(myId));
    }

    public QuestionnaireResponseDto getQuestionnaire(String myId, String questionnaireId) {
        User me = userRepository.findById(myId).get();

        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(() -> new EntityNotFoundException("no questionnaire for id (" + questionnaireId + ")"));

        boolean isReceiver = myId.equals(questionnaire.getReceiverId());
        boolean isSender = myId.equals(questionnaire.getCreateUserId());
        if( !isReceiver && !isSender ) {
            throw new InvalidException("you are not the creator or receiver of the questionnaire(id: " + questionnaireId + ")!");
        }

        return questionMapper.toQuestionnaireResponseDto(questionnaire, me.getBookmarks());
    }

    private Integer countValidAnswer(List<String> answers) {
        int count = 0;
        for (String answer : answers) {
            if (answer != null && !answer.trim().equals("")) count += 1;
        }
        return count;
    }

    private Questionnaire fillQuestionnaire(Questionnaire questionnaire,
                                            AnswerQuestionnaireDto questionnaireAnswerDto) {

        Map<String, String> answers = questionnaireAnswerDto.getAnswers();
        List<String> answerValues = new ArrayList<>(answers.values());
        LocalDateTime updatedAt = questionnaireAnswerDto.getUpdatedAt();

        // TODO: 응답이 빈 질문이 있나 체크
        int questionCount = questionnaire.getQuestions().size();
        int validAnswerCount = countValidAnswer(answerValues);

        questionnaire.setAnswers(answers);
        questionnaire.setUpdatedAt(updatedAt);
        if (questionCount == validAnswerCount) {
            questionnaire.setAnsweredAt(updatedAt);
            questionnaire.setCompleteFlag(Boolean.TRUE);
        } else {
            questionnaire.setCompleteFlag(Boolean.FALSE);
        }

        return questionnaire;
    }
}
