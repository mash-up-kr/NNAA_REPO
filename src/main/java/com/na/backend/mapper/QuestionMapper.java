package com.na.backend.mapper;

import com.na.backend.dto.*;
import com.na.backend.entity.Question;
import com.na.backend.entity.Questionnaire;
import com.na.backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionMapper {

    public Question toQuestion(NewQuestionDto newQuestionDto) {
        return Question.builder()
                .type(newQuestionDto.getType())
                .category(newQuestionDto.getCategory())
                .content(newQuestionDto.getContent())
                .choices(newQuestionDto.getChoices())
                .build();
    }

    public Questionnaire toQuestionnaire(String createUserId, QuestionnaireDto questionnaireDto) {
        return Questionnaire.builder()
                .createUserId(createUserId)
                .receiverId(questionnaireDto.getReceiverId())
                .category(questionnaireDto.getCategory())
                .questions(questionnaireDto.getQuestions())
                .createdAt(questionnaireDto.getCreatedAt())
                .build();
    }

    public QuestionDto toQuestionDto(Question question) {
        return QuestionDto.builder()
                .type(question.getType())
                .category(question.getCategory())
                .content(question.getContent())
                .choices(question.getChoices())
                .build();
    }

    public QuestionnaireDto toQuestionnaireDto(Questionnaire questionnaire) {
        return QuestionnaireDto.builder()
                .receiverId(questionnaire.getReceiverId())
                .category(questionnaire.getCategory())
                .createdAt(questionnaire.getCreatedAt())
                .build();
    }

    public List<InboxQuestionnaireDto> toInboxQuestionnaireDtos(List<Questionnaire> questionnaires) {
        List<InboxQuestionnaireDto> inboxQuestionnaireDtos = new ArrayList<>();

        questionnaires.forEach(questionnaire ->
            inboxQuestionnaireDtos.add(InboxQuestionnaireDto.builder()
                                    .senderId(questionnaire.getCreateUserId())
                                    .senderName(questionnaire.getCreateUserName())
                                    .category(questionnaire.getCategory())
                                    .questionsCount(questionnaire.getQuestions().size())
                                    .build())
        );

        return inboxQuestionnaireDtos;
    }

    public List<OutboxQuestionnaireDto> toOutboxQuestionnaireDtos(List<Questionnaire> questionnaires) {
        List<OutboxQuestionnaireDto> outboxQuestionnaireDtos = new ArrayList<>();

        questionnaires.forEach(questionnaire ->
                outboxQuestionnaireDtos.add(OutboxQuestionnaireDto.builder()
                        .receiverId(questionnaire.getReceiverId())
                        .receiverName(questionnaire.getReceiverName())
                        .category(questionnaire.getCategory())
                        .questionsCount(questionnaire.getQuestions().size())
                        .build())
        );

        return outboxQuestionnaireDtos;
    }
}
