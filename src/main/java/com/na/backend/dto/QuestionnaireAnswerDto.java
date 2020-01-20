package com.na.backend.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireAnswerDto {
    @ApiModelProperty(example = "{\n" +
            "\t\t\"1\": \"웃겼던 추억 답변\",\n" +
            "\t\t\"2\": \"흑역사 답변\"\n" +
            "\t}")
    private Map<String, String> answers;

    @ApiModelProperty(example = "false")
    private Boolean completeFlag;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime answeredAt;

    @Builder
    public QuestionnaireAnswerDto(Map<String, String> answers,
                                  Boolean completeFlag,
                                  LocalDateTime createdAt,
                                  LocalDateTime updatedAt,
                                  LocalDateTime answeredAt) {
        this.answers = answers;
        this.completeFlag = completeFlag;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.answeredAt = answeredAt;
    }
}