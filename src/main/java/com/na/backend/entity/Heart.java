package com.na.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Heart {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private Integer userId;

    @Column(nullable=false)
    private String questionId;

    @Builder
    public Heart(Integer userId, String questionId){
        this.userId = userId;
        this.questionId = questionId;
    }

}
