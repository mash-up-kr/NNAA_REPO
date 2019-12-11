package com.na.backend.dto;

import lombok.Builder;

import java.util.List;

public class BookmarkedQuestionDto {
     //질문 내용
    private String content;


    @Builder
    public BookmarkedQuestionDto(String content){
        this.content=content;

    }

}
