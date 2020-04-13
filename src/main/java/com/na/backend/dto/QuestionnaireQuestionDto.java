package com.na.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireQuestionDto {

    @ApiModelProperty(example = "객관식")
    private String type;
    @ApiModelProperty(example = "너가 생각하는 나의 성격은?")
    private String content;
    @ApiModelProperty(example = "{\n" +
            "        \"a\" : \"예민보스\",\n" +
            "            \"b\" : \"덜렁대\",\n" +
            "            \"c\" : \"비밀스러워\",\n" +
            "            \"d\" : \"재밌어\"\n" +
            "    }")
    private Map<String, String> choices;

    @Builder
    public QuestionnaireQuestionDto(String type, String content, Map<String, String> choices) {
        this.type = type;
        this.content = content;
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", choices=" + choices +
                '}';
    }
}