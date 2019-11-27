package com.na.backend.controller;

import com.na.backend.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(value="질문 API", description = "질문관련")
@Controller
@RequestMapping("/question")
public class QuestionController {

    @ApiOperation(value = "질문 입력하기", notes = "질문등록하기 ")
    @PostMapping
    public ResponseEntity<Map<String, String>> insertQuestion(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "미리 setup 질문들 보여주기", notes = "질문 고를때 질문들 내려주기  ")
    @GetMapping
    public ResponseEntity<List<UserDto>> getQuestionList(@RequestParam String category) {

        List<UserDto> tmp = new ArrayList<>();
        return ResponseEntity.status(HttpStatus.OK).body(tmp);
    }

    @ApiOperation(value = "즐겨찾기해둔 질문들 보여주기 ", notes = "질문 고를때 즐겨찾기 질문들 보여주기 ")
    @GetMapping("/bookmark")
    public void getBookmarkList() {

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
