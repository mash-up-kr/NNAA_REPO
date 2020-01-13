package com.na.backend.controller;

import com.na.backend.dto.EmailDto;
import com.na.backend.dto.ProfileDto;
import com.na.backend.dto.SocialDto;
import com.na.backend.entity.User;
import com.na.backend.service.UserService;
import com.na.backend.util.OAuthManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(value="user 인증 API")
@RestController
@RequestMapping("/user")
public class UserController {

    private final String HEADER_ID = "id";
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
}