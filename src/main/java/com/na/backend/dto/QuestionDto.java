package com.na.backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDto {

    private String content;
    private String choices;

    @Builder
    public QuestionDto(String content, String choices){
        this.content = content;
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "content='" + content + '\'' +
                ", choices='" + choices + '\'' +
                '}';
    }
}
