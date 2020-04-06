package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutboxQuestionnaireDto {

    private String receiverId;
    private String receiverName;
    private String category;
    private Integer questionsCount;

    @Builder
    public OutboxQuestionnaireDto(String receiverId, String receiverName, String category, Integer questionsCount) {
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.category = category;
        this.questionsCount = questionsCount;
    }
}