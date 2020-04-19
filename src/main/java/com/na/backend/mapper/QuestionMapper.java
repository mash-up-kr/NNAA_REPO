package com.na.backend.mapper;

import com.na.backend.dto.*;
import com.na.backend.entity.Questionnaire;
import com.na.backend.entity.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuestionMapper {

    public Questionnaire toQuestionnaireWhenNew(User createUser, User receiver, QuestionnaireDto questionnaireDto) {
        return Questionnaire.builder()
                .createUserId(createUser.getId())
                .createUserName(createUser.getName())
                .receiverId(receiver.getId())
                .receiverName(receiver.getName())
                .completeFlag(Boolean.FALSE)
                .category(questionnaireDto.getCategory())
                .questions(questionnaireDto.getQuestions())
                .createdAt(questionnaireDto.getCreatedAt())
                .build();
    }

    public QuestionnaireResponseDto toQuestionnaireResponseDto(Questionnaire questionnaire, Map<String, BookmarkQuestionDto> myBookmarks) {
        // TODO: 질문이 즐겨찾기의 질문과 동일한지 체크하는 로직 함수로 분리
        Map<String, QuestionnaireQuestionDto> questionsWithoutBookmarkFlag = questionnaire.getQuestions();
        Map<String, QuestionnaireQuestionResponseDto> questionsWithBookmarkFlag = new HashMap<>();

        for(String key: questionsWithoutBookmarkFlag.keySet()) {
            QuestionnaireQuestionDto questionWithoutBookmarkFlag = questionsWithoutBookmarkFlag.get(key);

            StringBuilder questionString = new StringBuilder(questionWithoutBookmarkFlag.getContent());
            if(questionWithoutBookmarkFlag.getChoices() != null){
                for(String choiceString: questionWithoutBookmarkFlag.getChoices().values()) {
                    questionString.append(choiceString);
                }
            }
            int questionHash = questionString.toString().hashCode();

            questionsWithBookmarkFlag.put(key,
                    QuestionnaireQuestionResponseDto.builder()
                            .content(questionWithoutBookmarkFlag.getContent())
                            .choices(questionWithoutBookmarkFlag.getChoices())
                            .type(questionWithoutBookmarkFlag.getType())
                            .isBookmarked(myBookmarks.containsKey(String.valueOf(questionHash)))
                            .build());
        }

        return QuestionnaireResponseDto.builder()
                .id(questionnaire.getId())
                .createUserId(questionnaire.getCreateUserId())
                .createUserName(questionnaire.getCreateUserName())
                .receiverId(questionnaire.getReceiverId())
                .receiverName(questionnaire.getReceiverName())
                .completeFlag(questionnaire.getCompleteFlag())
                .category(questionnaire.getCategory())
                .questions(questionsWithBookmarkFlag)
                .answers(questionnaire.getAnswers())
                .createdAt(questionnaire.getCreatedAt())
                .updatedAt(questionnaire.getUpdatedAt())
                .answeredAt(questionnaire.getAnsweredAt())
                .build();
    }

    public List<InboxQuestionnaireDto> toInboxQuestionnaireDtos(List<Questionnaire> questionnaires) {
        List<InboxQuestionnaireDto> inboxQuestionnaireDtos = new ArrayList<>();

        questionnaires.forEach(questionnaire ->
                inboxQuestionnaireDtos.add(InboxQuestionnaireDto.builder()
                        .id(questionnaire.getId())
                        .createdAt(questionnaire.getCreatedAt())
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
                        .id(questionnaire.getId())
                        .createdAt(questionnaire.getCreatedAt())
                        .receiverId(questionnaire.getReceiverId())
                        .receiverName(questionnaire.getReceiverName())
                        .category(questionnaire.getCategory())
                        .questionsCount(questionnaire.getQuestions().size())
                        .build())
        );

        return outboxQuestionnaireDtos;
    }
}
