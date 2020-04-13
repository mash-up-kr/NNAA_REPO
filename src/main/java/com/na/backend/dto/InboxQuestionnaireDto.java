package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class InboxQuestionnaireDto {

    private String id;
    private LocalDateTime createdAt;
    private String senderId;
    private String senderName;
    private String category;
    private Integer questionsCount;

    @Builder
    public InboxQuestionnaireDto(String id,
                                 LocalDateTime createdAt,
                                 String senderId,
                                 String senderName,
                                 String category,
                                 Integer questionsCount) {
        this.id = id;
        this.createdAt = createdAt;
        this.senderId = senderId;
        this.senderName = senderName;
        this.category = category;
        this.questionsCount = questionsCount;
    }
}