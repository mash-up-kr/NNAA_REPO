package com.na.backend.controller;

import com.na.backend.dto.UserInfo;
import com.na.backend.entity.User;
import com.na.backend.service.UserService;
import com.na.backend.util.OAuthManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;


@Api(value="user 인증 API")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation(value = "유저 로그인", notes = "등록된 사용자가 아니면 유저 등록")
    @GetMapping(value = "/login")
    public ResponseEntity<UserInfo> addUser(HttpServletRequest request) throws GeneralSecurityException, IOException {
        String accessToken = request.getHeader("Authorization");
        accessToken = accessToken.replace("Bearer ","");
        String provider = request.getHeader("provider");

        User user = OAuthManager.getUser(provider, accessToken);
        Long userId = user.getUserId();
        System.out.println(userId);

        if (userService.isUser(userId)) {
            return ResponseEntity.status(HttpStatus.OK).body(userService.findByUserId(userId));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(user));
        }
    }

}