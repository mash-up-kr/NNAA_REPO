package com.na.backend.service;

import com.na.backend.dto.InboxQuestionnaireDto;
import com.na.backend.dto.OutboxQuestionnaireDto;
import com.na.backend.dto.QuestionnaireAnswerDto;
import com.na.backend.dto.QuestionnaireDto;
import com.na.backend.entity.Questionnaire;
import com.na.backend.exception.EntityNotFoundException;
import com.na.backend.exception.InvalidException;
import com.na.backend.mapper.QuestionMapper;
import com.na.backend.repository.QuestionnaireRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionnaireService {

    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionMapper questionMapper;

    public QuestionnaireService(QuestionnaireRepository questionnaireRepository, QuestionMapper questionMapper) {
        this.questionnaireRepository = questionnaireRepository;
        this.questionMapper = questionMapper;
    }

    public Questionnaire createQuestionnaire(String createUserId, QuestionnaireDto questionnaireDto) {

        return questionnaireRepository.insert(questionMapper.toQuestionnaire(createUserId, questionnaireDto));
    }

    @Transactional
    public Questionnaire insertAnswer(String questionnaireId, QuestionnaireAnswerDto questionnaireAnswerDto) {
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

    public QuestionnaireDto getQuestionnaire(String questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId)
                                                             .orElseThrow(()->new InvalidException("invalid questionnaire"));
        return questionMapper.toQuestionnaireDto(questionnaire);
    }

    private Questionnaire fillQuestionnaire(Questionnaire questionnaire,
                                            QuestionnaireAnswerDto questionnaireAnswerDto) {
        questionnaire.setAnswers(questionnaireAnswerDto.getAnswers());

        Boolean isCompleted = questionnaireAnswerDto.getCompleteFlag();
        questionnaire.setCompleteFlag(isCompleted);

        if (isCompleted) {
            questionnaire.setAnsweredAt(questionnaireAnswerDto.getAnsweredAt());
        } else {
            questionnaire.setUpdatedAt(questionnaireAnswerDto.getUpdatedAt());
        }

        return questionnaire;
    }
}
