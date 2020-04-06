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

    public Questionnaire toQuestionnaireWhenNew(User createUser, User receiver, NewQuestionnaireDto newQuestionnaireDto) {
        return Questionnaire.builder()
                .createUserId(createUser.getId())
                .createUserName(createUser.getName())
                .receiverId(receiver.getId())
                .receiverName(receiver.getName())
                .completeFlag(Boolean.FALSE)
                .category(newQuestionnaireDto.getCategory())
                .questions(newQuestionnaireDto.getQuestions())
                .createdAt(newQuestionnaireDto.getCreatedAt())
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
