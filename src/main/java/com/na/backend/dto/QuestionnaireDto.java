package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireDto {

    private String receiverId;
    private String category;
    private LocalDateTime createdAt;
    private Map<String, QuestionnaireQuestionDto> questions;

    @Builder
    public QuestionnaireDto(String receiverId,
                            String category,
                            LocalDateTime createdAt,
                            Map<String, QuestionnaireQuestionDto> questions) {
        this.receiverId = receiverId;
        this.category = category;
        this.createdAt = createdAt;
        this.questions = questions;
    }
}