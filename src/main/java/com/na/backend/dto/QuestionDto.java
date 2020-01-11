package com.na.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDto {
    @ApiModelProperty(example = "주관식")
    private String type;
    @ApiModelProperty(example = "친구")
    private String category;
    @ApiModelProperty(example = "가장 흑역사라고 생각하는 나와의 추억은?")
    private String content;
    @ApiModelProperty(example = "")
    private Map<String, String> choices;

    @Builder
    public QuestionDto(String type, String category, String content, Map<String, String> choices){
        this.type = type;
        this.category = category;
        this.content = content;
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", choices=" + choices +
                '}';
    }
}
