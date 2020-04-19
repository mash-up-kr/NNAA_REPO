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
public class QuestionnaireResponseDto {
// Questionnaire 와 동일하나 questions 만 isBookmarked 추가된 형태
    private String id;
    private String createUserId;
    private String createUserName;
    private String receiverId;
    private String receiverName;
    private String category;
    private Boolean completeFlag;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime answeredAt;
    private Map<String, QuestionnaireQuestionResponseDto> questions;
    private Map<String,String> answers;

    @Builder
    public QuestionnaireResponseDto(String id,
                                    String createUserId,
                                    String createUserName,
                                    String receiverId,
                                    String receiverName,
                                    String category,
                                    Boolean completeFlag,
                                    LocalDateTime createdAt,
                                    LocalDateTime updatedAt,
                                    LocalDateTime answeredAt,
                                    Map<String, QuestionnaireQuestionResponseDto> questions,
                                    Map<String, String> answers) {
        this.id = id;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.category = category;
        this.completeFlag = completeFlag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.answeredAt = answeredAt;
        this.questions = questions;
        this.answers = answers;
    }
}