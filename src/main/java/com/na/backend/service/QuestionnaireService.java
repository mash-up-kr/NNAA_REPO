package com.na.backend.service;

import com.na.backend.dto.InboxQuestionnaireDto;
import com.na.backend.dto.OutboxQuestionnaireDto;
import com.na.backend.dto.QuestionnaireAnswerDto;
import com.na.backend.dto.QuestionnaireDto;
import com.na.backend.entity.Questionnaire;
import com.na.backend.entity.User;
import com.na.backend.exception.EntityNotFoundException;
import com.na.backend.exception.InvalidException;
import com.na.backend.mapper.QuestionMapper;
import com.na.backend.repository.QuestionnaireRepository;
import com.na.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Questionnaire createQuestionnaire(String createUserId, NewQuestionnaireDto newQuestionnaireDto) {
        User createUser = userRepository.findById(createUserId).get();
        User receiver = userRepository.findById(newQuestionnaireDto.getReceiverId()).get();

        return questionnaireRepository.insert(questionMapper.toQuestionnaireWhenNew(createUser, receiver, newQuestionnaireDto));
    }

    @Transactional
    public Questionnaire insertAnswer(String questionnaireId, AnswerQuestionnaireDto questionnaireAnswerDto) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                                                             .orElseThrow(()-> new InvalidException("invalid questionnaire"));

        return questionnaireRepository.save(fillQuestionnaire(questionnaire, questionnaireAnswerDto));
    }

    public List<OutboxQuestionnaireDto> getOutboxQuestionnaires(String myId) {

        return questionMapper.toOutboxQuestionnaireDtos(questionnaireRepository.findByCreateUserId(myId));
    }

    public List<InboxQuestionnaireDto> getInboxQuestionnaires(String myId) {

        return questionMapper.toInboxQuestionnaireDtos(questionnaireRepository.findByReceiverId(myId));
    }

    public Questionnaire getQuestionnaire(String questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                .orElseThrow(RuntimeException::new);
        return questionnaire;
    }


    public QuestionnaireDto getQuestionnaire(String questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                                                             .orElseThrow(()->new InvalidException("invalid questionnaire"));
        return questionMapper.toQuestionnaireDto(questionnaire);
    }

    private Integer countValidAnswer(List<String> answers) {
        int count = 0;
        for(String answer : answers) {
            if( answer != null && !answer.trim().equals("") ) count += 1;
        }
        return count;
    }

    private Questionnaire fillQuestionnaire(Questionnaire questionnaire,
                                            AnswerQuestionnaireDto questionnaireAnswerDto) {

        Map<String, String> answers = questionnaireAnswerDto.getAnswers();
        List<String> answerValues = new ArrayList<>(answers.values());
        LocalDateTime updatedAt = questionnaireAnswerDto.getUpdatedAt();

        // TODO: 응답이 빈 질문이 있나 체크
        Integer questionCount = questionnaire.getQuestions().size();
        Integer validAnswerCount = countValidAnswer(answerValues);

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
