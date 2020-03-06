package com.na.backend.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class NewQuestionDto {

    @ApiModelProperty(example = "객관식")
    private String type;
    @ApiModelProperty(example = "친구")
    private String category;
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
    public NewQuestionDto(String type, String content, String category, Map<String, String> choices) {
        this.type = type;
        this.category = category;
        this.content = content;
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "NewQuestionDto{" +
                "type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", choices=" + choices +
                '}';
    }
}