package com.na.backend.controller;

import com.na.backend.dto.EmailDto;
import com.na.backend.dto.SocialDto;
import com.na.backend.entity.User;
import com.na.backend.service.UserService;
import com.na.backend.util.OAuthManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@Api(value="friend API")
@RestController
@RequestMapping("/friend")
public class FriendController {

    private UserService userService;

    public FriendController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation(value = "닉네임으로 유저찾기", notes = "닉네임으로 다른 유저 찾기")
    @GetMapping(value = "/nickname/{nickname}")
    public ResponseEntity<String> searchUserByNickname(@PathVariable String nickname) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getIdByNickname(nickname));
    }

    @ApiOperation(value = "친구 등록하기", notes = "id로 내 친구 등록")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> addFriend(@PathVariable String id) {
        String myId = "";
        userService.addFriendById(myId, id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}