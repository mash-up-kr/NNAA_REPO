package com.na.backend.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Setter
@Getter
@AllArgsConstructor
@Document(collection = "quesiton_message")
public class Questionnaire {


    @Id
    private String id;

    private String sender;

    private String receiver;

    private String questions;

    @Builder
    public Questionnaire(String sender, String receiver, String questions ){
        this.sender = sender;
        this.receiver = receiver;
        this.questions = questions;

    }



    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", questions='" + questions + '\'' +
                '}';
    }



}
