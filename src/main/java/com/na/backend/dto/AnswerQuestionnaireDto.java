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
public class AnswerQuestionnaireDto {
    @ApiModelProperty(example = "{\n" +
            "\t\t\"1\": \"웃겼던 추억 답변\",\n" +
            "\t\t\"2\": \"흑역사 답변\"\n" +
            "\t}")
    private Map<String, String> answers;
    private LocalDateTime updatedAt;

    @Builder
    public AnswerQuestionnaireDto(Map<String, String> answers,
                                  LocalDateTime updatedAt) {
        this.answers = answers;
        this.updatedAt = updatedAt;
    }
}