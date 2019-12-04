package com.na.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendQuestionMessageDto {

    private Integer sender;
    private Integer receiver;
    private LocalDateTime senderTime;
    private List<String> questions;

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
        return "SendQuestionMessageDto{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", senderTime=" + senderTime +
                ", questions=" + questions +
                ", content='" + content + '\'' +
                ", choices='" + choices + '\'' +
                '}';
    }
}