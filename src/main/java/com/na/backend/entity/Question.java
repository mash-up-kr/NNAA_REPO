package com.na.backend.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;


@Document(collection = "question")
@Setter
@Getter
public class Question {

    @Id
    private String id;

    private String content;

    private String category;

    private String type;

    private String choice1;

    private String choice2;

    private String choice3;

    private String choice4;
}
