package com.na.backend.controller;

import com.na.backend.dto.*;
import com.na.backend.entity.Question;
import com.na.backend.entity.User;
import com.na.backend.service.QuestionService;
import com.na.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Api(value="질문 API")
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final String HEADER_ID = "id";
    private final QuestionService questionService;
    private final UserService userService;


    public QuestionController(QuestionService questionService ,UserService userService) {
        this.questionService = questionService;
        this.userService=userService;
    }

    @ApiOperation(value = "질문 입력하기", notes = "질문등록하기 ")
    @PostMapping
    public ResponseEntity<Question> insertQuestion(@RequestBody NewQuestionDto newQuestionDto){
        Question newQuestion = questionService.insertNewQuestion(newQuestionDto);
        return ResponseEntity.status(HttpStatus.OK).body(newQuestion);
    }

    // TODO: category 나 type 둘 중 하나만 입력할 경우, 아예 입력 안할 경우에 대한 처리
    @ApiOperation(value = "category, type 별 미리 setup 질문들 보여주기", notes = "질문 고를때 질문들 내려주기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category", value = "문제 카테고리", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "문제 유형", paramType = "query")
    })
    @GetMapping
    public ResponseEntity<List<Question>> getQuestionList(@RequestParam(defaultValue = "all") String category, @RequestParam(defaultValue = "all") String type) {
        List<Question> recommendationQuestions = questionService.getRecommendationQuestionList(category, type);
        return ResponseEntity.status(HttpStatus.OK).body(recommendationQuestions);
    }



    @ApiOperation(value = "즐겨찾기해둔 질문들 보여주기 ", notes = "질문 고를때 즐겨찾기 질문들 보여주기 ")
    @GetMapping("/bookmark")
    public ResponseEntity<List<String>> getBookmarkList(HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);
        List<String> userBookmark = userService.getUserBookmark(myId);

        return ResponseEntity.status(HttpStatus.OK).body(userBookmark);
    }


    @ApiOperation(value = "즐겨찾기 등록", notes = "질문 즐겨찾기 등록하기 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "문제 번호", paramType = "path", required = true)
    })
    @PatchMapping("/bookmark/{questionId}")
    public ResponseEntity<Void> addBookmark(@PathVariable String questionId, HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);
        userService.addBookmark(myId,questionId);

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @ApiOperation(value = "즐겨찾기 취소", notes = "질문 즐겨찾기 취소하기 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "문제 번호", paramType = "path", required = true)
    })
    @DeleteMapping("/bookmark/{questionId}")
    public ResponseEntity<Void> removeHeart(@PathVariable String questionId, HttpServletRequest request){
        String myId = request.getHeader(HEADER_ID);
        userService.dropBookmark(myId,questionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

