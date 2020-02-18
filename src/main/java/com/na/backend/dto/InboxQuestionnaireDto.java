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
public class InboxQuestionnaireDto {

    private String senderId;
    private String senderName;
    private String category;
    private Integer questionsCount;

    @Builder
    public InboxQuestionnaireDto(String senderId, String senderName, String category, Integer questionsCount) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.category = category;
        this.questionsCount = questionsCount;
    }
}