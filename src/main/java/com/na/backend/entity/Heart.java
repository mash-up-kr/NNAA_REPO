package com.na.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@Getter
public class Heart {
    @Id
    private Long id;

    private Integer userId;

    private String questionId;

    @Builder
    public Heart(Integer userId, String questionId){
        this.userId = userId;
        this.questionId = questionId;
    }

}
