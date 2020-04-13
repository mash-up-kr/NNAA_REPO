package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OutboxQuestionnaireDto {

    private String id;
    private LocalDateTime createdAt;
    private String receiverId;
    private String receiverName;
    private String category;
    private Integer questionsCount;

    @Builder
    public OutboxQuestionnaireDto(String id,
                                  LocalDateTime createdAt,
                                  String receiverId,
                                  String receiverName,
                                  String category,
                                  Integer questionsCount) {
        this.id = id;
        this.createdAt = createdAt;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.category = category;
        this.questionsCount = questionsCount;
    }
}