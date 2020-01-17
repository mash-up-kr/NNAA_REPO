package com.na.backend.controller;

import com.na.backend.dto.EmailDto;
import com.na.backend.dto.ProfileDto;
import com.na.backend.dto.SocialDto;
import com.na.backend.entity.User;
import com.na.backend.service.UserService;
import com.na.backend.util.OAuthManager;
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

    @ApiOperation(value = "회원가입/로그인(kakao, facebook)", notes = "등록된 사용자가 아니면 유저 등록(회원가입)/ 등록된 사용자면 로그인")
    @PostMapping(value = "/social")
    public ResponseEntity<Void> socialLogin(@RequestBody SocialDto socialDto, HttpServletResponse response) {
        String uid = OAuthManager.getUid(socialDto.getProvider(), socialDto.getAccessToken());
        User user = userService.getUserBySocialService(uid);

        response.setHeader("id", user.getId());
        response.setHeader("token", user.getToken());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "회원가입/로그인(이메일)", notes = "등록된 사용자가 아니면 유저 등록(회원가입)/ 등록된 사용자면 로그인")
    @PostMapping(value = "/email")
    public ResponseEntity<Void> emailLogin(@RequestBody EmailDto emailDto, HttpServletResponse response) {
        User user = userService.getUserByEmail(emailDto);

        response.setHeader("id", user.getId());
        response.setHeader("token", user.getToken());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "닉네임 등록/수정하기", notes = "닉네임 등록 및 수정하기")
    @PatchMapping(value = "/nickname")
    public ResponseEntity<User> updateNickname(@RequestBody ProfileDto profileDto, HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);
        User updatedUser = userService.updateNickname(myId, profileDto);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
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