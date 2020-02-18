package com.na.backend.controller;

import com.na.backend.dto.LogInDto;
import com.na.backend.dto.SignUpDto;
import com.na.backend.entity.User;
import com.na.backend.exception.AlreadyExistsException;
import com.na.backend.exception.EntityNotFoundException;
import com.na.backend.exception.InvalidStringException;
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


@Api(value="user 인증 API")
@RestController
@RequestMapping("/user")
public class UserController {

    private static final String HEADER_ID = "id";
    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation(value = "회원가입(이메일)", notes = "등록된 사용자가 아니면 유저 등록(회원가입)/ 등록된 사용자면 로그인")
    @GetMapping(value = "/email/sign_up")
    public ResponseEntity<Void> emailSignUp(@RequestBody SignUpDto signUpDto, HttpServletResponse response) {

        if (userService.isInvalidEmailPattern(signUpDto.getEmail())) {
            throw new InvalidStringException("유효하지 않은 이메일 양식입니다");
        }

        if (userService.isEmailUser(signUpDto.getEmail())) {
            String message = "해당 email(" + signUpDto.getEmail() + "을 가진 유저가 이미 존재합니다";
            throw new AlreadyExistsException(message);
        } else {
            User user = userService.addUserByEmail(signUpDto);

            response.setHeader("id", user.getId());
            response.setHeader("token", user.getToken());

            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @ApiOperation(value = "로그인(이메일)", notes = "입력한 email 유저가 있으면 로그인 / 없으면 에러 ")
    @PostMapping(value = "/email/sign_in")
    public ResponseEntity<String> emailLogin(@RequestBody LogInDto loginDto, HttpServletResponse response) {

        if (userService.isEmailUser(loginDto.getEmail())) {
            User user = userService.getUserByEmail(loginDto);

            response.setHeader("id", user.getId());
            response.setHeader("token", user.getToken());

            return ResponseEntity.status(HttpStatus.OK).body("로그인 성공!");
        } else {
            String message = "해당 이메일(" + loginDto.getEmail() + ")을 가진 유저가 없습니다.";
            throw new EntityNotFoundException(message);
        }
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