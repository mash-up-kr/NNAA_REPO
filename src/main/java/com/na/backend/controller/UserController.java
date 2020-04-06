package com.na.backend.controller;

import com.na.backend.dto.LogInDto;
import com.na.backend.dto.SignUpDto;
import com.na.backend.dto.UserAuthDto;
import com.na.backend.dto.UserInfoDto;
import com.na.backend.entity.Question;
import com.na.backend.exception.*;
import com.na.backend.service.QuestionService;
import com.na.backend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Api(value = "user 인증 API")
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String HEADER_ID = "id";
    private UserService userService;
    private QuestionService questionService;

    public UserController(UserService userService,
                          QuestionService questionService) {
        this.userService = userService;
        this.questionService = questionService;
    }

    @ApiOperation(value = "회원가입(이메일)", notes = "등록된 사용자가 아니면 유저 등록(회원가입)/ 등록된 사용자면 로그인")
    @PostMapping(value = "/email")
    public ResponseEntity<UserAuthDto> emailSignUp(@RequestBody SignUpDto signUpDto, HttpServletResponse response) {
        String email = signUpDto.getEmail();
        String name = signUpDto.getName();

        if (userService.isInvalidNamePattern(name)) {
            throw new InvalidStringException("유효하지 않은 이름 형식입니다(" + name + ")");
        }

        if (userService.isInvalidEmailPattern(email)) {
            throw new InvalidStringException("유효하지 않은 이메일 형식입니다(" + email + ")");
        }

        if (userService.isEmailUser(email)) {
            String message = "해당 email(" + email + ")을 가진 유저가 이미 존재합니다";
            throw new AlreadyExistsException(message);
        } else {
            UserAuthDto userAuthDto = userService.addUserByEmail(signUpDto);

            // TODO: 삭제 예정 ( 헤더가 아닌 바디에 넣기로 )
            response.setHeader("id", userAuthDto.getId());
            response.setHeader("token", userAuthDto.getToken());

            return ResponseEntity.status(HttpStatus.OK).body(userAuthDto);
        }
    }

    @ApiOperation(value = "로그인(이메일)", notes = "입력한 email 유저가 있으면 로그인 / 없으면 에러 ")
    @PostMapping(value = "/email/sign_in")
    public ResponseEntity<UserAuthDto> emailLogin(@RequestBody LogInDto loginDto, HttpServletResponse response) {

        if (userService.isInvalidEmailPattern(loginDto.getEmail())) {
            throw new InvalidStringException("유효하지 않은 이메일 양식입니다");
        }

        if (userService.isEmailUser(loginDto.getEmail())) {
            UserAuthDto userAuthDto = userService.getUserByEmail(loginDto);

            // TODO: 삭제 예정 ( 헤더가 아닌 바디에 넣기로 )
            response.setHeader("id", userAuthDto.getId());
            response.setHeader("token", userAuthDto.getToken());

            return ResponseEntity.status(HttpStatus.OK).body(userAuthDto);
        } else {
            String message = "해당 이메일(" + loginDto.getEmail() + ")을 가진 유저가 없습니다.";
            throw new EntityNotFoundException(message);
        }
    }

    @ApiOperation(value = "비밀번호 재설정 이메일 보내기", notes = "비밀번호 재설정할 링크를 받을 이메일을 넘겨준다")
    @GetMapping(value = "/password")
    public ResponseEntity<Void> sendResetPasswordEmail(@RequestParam String email) {
        if (userService.isEmailUser(email)) {
            userService.sendResetPasswordEmail(email);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            String message = "해당 이메일(" + email + ")을 가진 유저가 없습니다.";
            throw new EntityNotFoundException(message);
        }
    }

    @ApiOperation(value = "로그인한 사용자 비밀번호 재설정하기", notes = "현재 로그인되어있는 사용자의 id를 path 에 넣어 보낸다")
    @PatchMapping(value = "{userId}/change_password")
    public ResponseEntity<String> resetPasswordForLoginUser(@PathVariable String userId, @RequestParam String newPassword, @RequestParam String newPasswordAgain) {

        if (newPassword.equals(newPasswordAgain)){
            userService.resetPasswordForLoginUser(userId,newPassword,newPasswordAgain);
        }else{
            throw new MismatchException("Re-entered password does not match new password");
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "로그인(카카오/페이스북)", notes = "추후 제작")
    @GetMapping(value = "/social")
    public ResponseEntity<String> socialLogin(@RequestParam String provider, @RequestParam String token) {
        return ResponseEntity.status(HttpStatus.OK).body("소셜로그인 아직 제공하지 않음");
    }

    @ApiOperation(value = "이름으로 유저찾기", notes = "이름으로 다른 유저 찾기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "검색할 이름", paramType = "query", required = true)
    })
    @GetMapping
    public ResponseEntity<List<UserInfoDto>> searchUserByName(@RequestParam String name) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.findUsers(name));
    }

    @ApiOperation(value = "즐겨찾기해둔 질문들 보여주기 ", notes = "질문 고를때 즐겨찾기 질문들 보여주기 ")
    @GetMapping("/bookmark")
    public ResponseEntity<List<Question>> getBookmarkList(HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserBookmark(myId));
    }

    @ApiOperation(value = "즐겨찾기 등록", notes = "질문 즐겨찾기 등록하기 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "문제 번호", paramType = "path", required = true)
    })
    @PatchMapping("/bookmark/{questionId}")
    public ResponseEntity<Void> addBookmark(@PathVariable String questionId, HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);

        if (questionService.isInvalidQuestionId(questionId)) {
            throw new InvalidException("Question id(" + questionId + ") is invalid");
        }

        userService.addBookmark(myId, questionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "즐겨찾기 취소", notes = "질문 즐겨찾기 취소하기 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "questionId", value = "문제 번호", paramType = "path", required = true)
    })
    @DeleteMapping("/bookmark/{questionId}")
    public ResponseEntity<Void> removeHeart(@PathVariable String questionId, HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);
        userService.dropBookmark(myId, questionId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}