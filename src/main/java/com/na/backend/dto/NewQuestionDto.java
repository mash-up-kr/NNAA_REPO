package com.na.backend.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class NewQuestionDto {

    private String type;
    private String category;
    private String content;
    private Map<String, String> choices;

    @Builder
    public NewQuestionDto(String type, String content, String category, Map<String, String> choices){
        this.type = type;
        this.category = category;
        this.content = content;
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "NewQuestionDto{" +
                "type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", choices=" + choices +
                '}';
    }
}