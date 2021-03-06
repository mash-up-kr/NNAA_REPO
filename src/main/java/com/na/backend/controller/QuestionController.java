package com.na.backend.controller;

import com.na.backend.dto.QuestionResponseDto;
import com.na.backend.exception.InvalidCategoryException;
import com.na.backend.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "질문 API")
@Controller
@RequestMapping("/question")
public class QuestionController {

    private static final String HEADER_ID = "id";
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

//    // TODO: category 나 type 둘 중 하나만 입력할 경우, 아예 입력 안할 경우에 대한 처리
//    @ApiOperation(value = "category, type 별 미리 setup 질문들 보여주기", notes = "질문 고를때 질문들 내려주기")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "category", value = "문제 카테고리", paramType = "query"),
//            @ApiImplicitParam(name = "type", value = "문제 유형", paramType = "query")
//    })
//    @GetMapping
//    public ResponseEntity<List<Question>> getQuestionList(@RequestParam(defaultValue = "all") String category, @RequestParam(defaultValue = "all") String type) {
//        List<Question> recommendationQuestions = questionService.getRecommendationQuestionList(category, type);
//
//        return ResponseEntity.status(HttpStatus.OK).body(recommendationQuestions);
//    }

    @ApiOperation(value = "문제지 첫 기본세팅", notes = "카테고리(나와 상대방의 관계) 선택 후 질문지의 기본 세팅을 위해 랜덤으로 질문 10개를 가져옵니다")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "문제 카테고리", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "문제 개수", paramType = "query")
    })
    @GetMapping("/random")
    public ResponseEntity<List<QuestionResponseDto>> getRandomQuestions(HttpServletRequest request,
                                                                        @RequestParam String category,
                                                                        @RequestParam(defaultValue = "10") Integer size) {
        String myId = request.getHeader(HEADER_ID);

        if (questionService.isInvalidCategory(category)) {
            throw new InvalidCategoryException("Invalid category(" + category + ")!");
        }

        List<QuestionResponseDto> randomQuestions = questionService.getRandomQuestions(myId, category, size);

        return ResponseEntity.status(HttpStatus.OK).body(randomQuestions);
    }
}

