package com.na.backend.mapper;

import com.na.backend.dto.*;
import com.na.backend.entity.Question;
import com.na.backend.entity.Questionnaire;
import com.na.backend.entity.User;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.*;

@Component
public class QuestionMapper {

    public Question toQuestion(QuestionnaireQuestionDto questionDto) {
        return Question.builder()
                .type(questionDto.getType())
                .content(questionDto.getContent())
                .choices(questionDto.getChoices())
                .build();
    }

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

    // 질문의 보기가 일치하는지 체크하는 함수
    private boolean sameChoices(Map<String, String> choices1, Map<String, String> choices2) {
        int size = choices1.size();
        if (size != choices2.size()) {
            return false;
        }

        for(String key: choices1.keySet()) {
            if ((choices1.get(key)).equals(choices2.get(key))) {
                continue;
            } else {
                return false;
            }
        }

        return true;
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
