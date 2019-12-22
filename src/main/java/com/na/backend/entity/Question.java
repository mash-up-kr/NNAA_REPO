package com.na.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class Question {

    @Id
    private String id;

    private String content;

    private String category;

    private String type;

    private String choices;

    @Builder
    public Question(String content, String category, String type, String choices){
        this.content = content;
        this.category = category;
        this.type = type;
        this.choices = choices;
    }



    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", choices='" + choices + '\'' +
                '}';
    }
}
