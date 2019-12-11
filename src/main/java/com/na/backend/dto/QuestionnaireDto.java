package com.na.backend.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireDto {

    private String id;

    private String sender;

    private String receiver;

    private String questions;

    @Builder
    public QuestionnaireDto(String sender, String receiver ,String questions){
        this.sender = sender;
        this.receiver = receiver;
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", questions='" + questions + '\'' +

                '}';
    }




}
