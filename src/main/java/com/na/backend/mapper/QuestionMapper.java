package com.na.backend.mapper;

import com.na.backend.dto.QuestionDto;
import com.na.backend.entity.Question;
import org.springframework.stereotype.Component;

@Component
public class QuestionMapper {

    public static QuestionDto toQuestionDto(Question question) {
        return new QuestionDto(question.getContent(), question.getChoices());
    }
}
