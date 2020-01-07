package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDto {

    private String type;
    private String category;
    private String content;
    private Map<String, String> choices;

    @Builder
    public QuestionDto(String type, String category, String content, Map<String, String> choices){
        this.type = type;
        this.category = category;
        this.content = content;
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", choices=" + choices +
                '}';
    }
}
