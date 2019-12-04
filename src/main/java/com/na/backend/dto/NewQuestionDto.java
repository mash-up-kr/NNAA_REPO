package com.na.backend.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewQuestionDto {

    private String category;
    private String type;
    private String content;
    private String choices;

//    @Builder
//    public NewQuestionDto(String content, String choices, String type, String category){
//        this.content = content;
//        this.choices = choices;
//        this.category = category;
//        this.type = type;
//    }

    @Override
    public String toString() {
        return "NewQuestionDto{" +
                "category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", choices='" + choices + '\'' +
                '}';
    }
}