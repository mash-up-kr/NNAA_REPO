package com.na.backend.controller;

import com.na.backend.dto.NewQuestionDto;
import com.na.backend.dto.QuestionDto;
import com.na.backend.dto.UserDto;
import com.na.backend.entity.Question;
import com.na.backend.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value="질문 API", description = "질문관련")
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @ApiOperation(value = "질문 입력하기", notes = "질문등록하기 ")
    @PostMapping
    public ResponseEntity<Question> insertQuestion(@RequestBody NewQuestionDto newQuestionDto){
        Question newQuestion = questionService.insertNewQuestion(newQuestionDto);
        return ResponseEntity.status(HttpStatus.OK).body(newQuestion);
    }

    @ApiOperation(value = "미리 setup 질문들 보여주기", notes = "질문 고를때 질문들 내려주기  ")
    @GetMapping
    public ResponseEntity<List<QuestionDto>> getQuestionList(@RequestParam String category, @RequestParam String type) {
        List<QuestionDto> recommendationQuestions = questionService.getRecommendationQuestionList();
        return ResponseEntity.status(HttpStatus.OK).body(recommendationQuestions);
    }

    @ApiOperation(value = "즐겨찾기해둔 질문들 보여주기 ", notes = "질문 고를때 즐겨찾기 질문들 보여주기 ")
    @GetMapping("/bookmark")
    public ResponseEntity<List<QuestionDto>> getBookmarkList() {
        String userId = ""; // 토큰으로부터 user_id 가져오기
        List<QuestionDto> likedQuestions = questionService.getHeartedQuestionList(userId);
        return ResponseEntity.status(HttpStatus.OK).body(likedQuestions);
    }

    @ApiOperation(value = "질문지 보내기", notes = "질문지를 1~20까지 작성후 전송 ")
    @PostMapping("/{receiverId}")
    public void askQuestion(@PathVariable Integer receiverId) {

    }

    @ApiOperation(value = "질문자가 질문지 리스트 보기", notes = "질문지 리스트 보기")
    @GetMapping("/sender/{senderId}/list")
    public void getSenderQuestionnaireList(@PathVariable Integer senderId) {

    }

    @ApiOperation(value = "응답자가 질문지 리스트 보기", notes = "질문지 리스트 보기")
    @GetMapping("/receiver/{receiverId}/list")
    public void getReceiverQuestionnaireList(@PathVariable Integer receiverId) {

    }

    @ApiOperation(value = "질문지 보기", notes = "질문지 하나만 보기! / 답변 보기 / 보관함에서 보기")
    @GetMapping("/list/{questionnaireId}")
    public void getQuestionnaire(@PathVariable Integer questionnaireId) {

    }

    @ApiOperation(value = "응답하기", notes = "질문지에 응답하기")
    @PutMapping("/answer/{questionnaireId}")
    public void answerQuestion(@PathVariable Integer questionnaireId) {

    }

    @ApiOperation(value = "좋아요 등록", notes = "좋아요 등록하기 ")
    @PostMapping("/heart/{questionId}")
    public void addHeart(@PathVariable Integer questionId) {

    }

    @ApiOperation(value = "좋아요 취소", notes = "좋아요 취소하기 ")
    @DeleteMapping("/heart/{questionId}")
    public void removeHeart(@PathVariable Integer questionId){


    }

}
