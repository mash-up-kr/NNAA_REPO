package com.na.backend.controller;

import com.na.backend.dto.QuestionnaireAnswerDto;
import com.na.backend.dto.QuestionnaireDto;
import com.na.backend.entity.Questionnaire;
import com.na.backend.service.QuestionnaireService;
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

@Api(value="질문지 API")
@Controller
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    private final String HEADER_ID = "id";
    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @ApiOperation(value = "질문지 보내기", notes = "질문지를 1~20까지 작성후 전송 ")
    @PostMapping
    public ResponseEntity<Questionnaire> sendQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);
        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.createQuestionnaire(myId, questionnaireDto));
    }

    @ApiOperation(value = "질문지에 답변하기", notes = "질문지에 응답하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "질문지 번호", paramType = "path", required = true)
    })
    @PatchMapping("/{questionnaireId}")
    public ResponseEntity<Questionnaire> answerQuestionnaire(@PathVariable String questionnaireId ,
                                                             @RequestBody QuestionnaireAnswerDto questionnaireAnswerDto) {

        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.insertAnswer(questionnaireId, questionnaireAnswerDto));
    }

    @ApiOperation(value = "질문지 보기", notes = "질문지 하나만 보기! / 답변 보기 / 보관함에서 보기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "질문지 번호", paramType = "path", required = true)
    })
    @GetMapping("/{questionnaireId}")
    public ResponseEntity<QuestionnaireDto> getQuestionnaire(@PathVariable String questionnaireId) {

        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.getQuestionnaire(questionnaireId));
    }


    @ApiOperation(value = "보낸 질문지 리스트 보기", notes = "질문자가 자신이 보낸 질문지들을 볼 수 있다. (현재 작성하고있는 질문지도 볼수 있다? => 추후 논의 필요) ")
    @GetMapping("/outbox")
    public ResponseEntity<List<QuestionnaireDto>> getOutboxQuestionnaireList(HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);

        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.getOutboxQuestionnaires(myId));
    }

    @ApiOperation(value = "받은 질문지 리스트 보기", notes = "응답자가 자신이 받은 질문지들과 현재 보내고 있는 질문지들을 볼 수 있다. ")
    @GetMapping("/inbox")
    public ResponseEntity<List<QuestionnaireDto>> getInboxQuestionnaireList(HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);

        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.getInboxQuestionnaires(myId));
    }

}

