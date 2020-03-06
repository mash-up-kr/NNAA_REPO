package com.na.backend.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireDto {

    private String createUserName;
    private String receiverId;
    private String receiverName;
    private String category;
    private LocalDateTime createdAt;
    private Map<String, String> questions;
    private Map<String, String> answers;

    @Builder
    public QuestionnaireDto(String receiverId,
                            String category,
                            LocalDateTime createdAt,
                            Map<String, String> questions,
                            Map<String, String> answers) {
        this.receiverId = receiverId;
        this.category = category;
        this.createdAt = createdAt;
        this.questions = questions;
        this.answers = answers;
    }

}