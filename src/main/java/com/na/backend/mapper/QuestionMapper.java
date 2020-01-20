package com.na.backend.mapper;

import com.na.backend.dto.NewQuestionDto;
import com.na.backend.dto.QuestionDto;
import com.na.backend.dto.QuestionnaireDto;
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

    public List<QuestionnaireDto> toQuestionnaireDtos(List<Questionnaire> questionnaires) {
        List<QuestionnaireDto> questionnaireDtos = new ArrayList<>();
        questionnaires.forEach(questionnaire ->
            questionnaireDtos.add(QuestionnaireDto.builder()
                                    .receiverId(questionnaire.getReceiverId())
                                    .category(questionnaire.getCategory())
                                    .createdAt(questionnaire.getCreatedAt())
                                    .questions(questionnaire.getQuestions())
                                    .answers(questionnaire.getAnswers())
                                    .build())
        );

        return questionnaireDtos;
    }
}
