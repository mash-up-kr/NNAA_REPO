package com.na.backend.entity;

import com.na.backend.dto.QuestionnaireQuestionDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;
import java.util.Map;

@Setter
@Getter
public class Questionnaire {

    @Id
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
    private Map<String, QuestionnaireQuestionDto> questions;
    private Map<String,String> answers;

    @Builder
    public Questionnaire(String id,
                         String createUserId,
                         String createUserName,
                         String receiverId,
                         String receiverName,
                         String category,
                         Boolean completeFlag,
                         LocalDateTime createdAt,
                         LocalDateTime updatedAt,
                         LocalDateTime answeredAt,
                         Map<String, QuestionnaireQuestionDto> questions,
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