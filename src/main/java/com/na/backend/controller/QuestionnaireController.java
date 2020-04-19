package com.na.backend.controller;

import com.na.backend.dto.*;
import com.na.backend.service.QuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "질문지 API")
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    private static final String HEADER_ID = "id";
    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @ApiOperation(value = "질문지 보내기", notes = "질문지를 상대방에게 전송한다")
    @PostMapping
    public ResponseEntity<QuestionnaireResponseDto> sendQuestionnaire(@RequestBody QuestionnaireDto questionnaireDto, HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);
        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.createQuestionnaire(myId, questionnaireDto));
    }

    // TODO: 답변하기 method 결정하기 Ex. PUT(멱등성) -> 전체 새로 업데이트 ? PATCH(비멱등성) -> 수정할 것만 수정 ? 현재는 PUT 임.. PATCH 가 더 안전해 보이긴 함
    @ApiOperation(value = "질문지에 답변하기", notes = "질문지에 응답하기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "질문지 번호", paramType = "path", required = true)
    })
    @PutMapping("/{questionnaireId}")
    public ResponseEntity<QuestionnaireResponseDto> answerQuestionnaire(HttpServletRequest request,
                                                                        @PathVariable String questionnaireId,
                                                                        @RequestBody AnswerQuestionnaireDto answerQuestionnaireDto) {
        String myId = request.getHeader(HEADER_ID);

        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.insertAnswer(myId, questionnaireId, answerQuestionnaireDto));
    }

    @ApiOperation(value = "질문지 보기", notes = "질문지 하나만 보기! / 답변 보기 / 보관함에서 보기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionnaireId", value = "질문지 번호", paramType = "path", required = true)
    })
    @GetMapping("/{questionnaireId}")
    public ResponseEntity<QuestionnaireResponseDto> getQuestionnaire(HttpServletRequest request,
                                                                     @PathVariable String questionnaireId) {
        String myId = request.getHeader(HEADER_ID);

        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.getQuestionnaire(myId, questionnaireId));
    }

    @ApiOperation(value = "보낸 질문지 리스트 보기", notes = "질문자가 자신이 보낸 질문지들을 볼 수 있다. (현재 작성하고있는 질문지도 볼수 있다? => 추후 논의 필요) ")
    @GetMapping("/outbox")
    public ResponseEntity<List<OutboxQuestionnaireDto>> getOutboxQuestionnaireList(HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);

        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.getOutboxQuestionnaires(myId));
    }

    @ApiOperation(value = "받은 질문지 리스트 보기", notes = "응답자가 자신이 받은 질문지들과 현재 보내고 있는 질문지들을 볼 수 있다. ")
    @GetMapping("/inbox")
    public ResponseEntity<List<InboxQuestionnaireDto>> getInboxQuestionnaireList(HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);

        return ResponseEntity.status(HttpStatus.OK).body(questionnaireService.getInboxQuestionnaires(myId));
    }

}

