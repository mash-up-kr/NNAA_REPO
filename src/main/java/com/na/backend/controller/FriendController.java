package com.na.backend.controller;

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


@Api(value="friend API")
@RestController
@RequestMapping("/friend")
public class FriendController {

    private final String HEADER_ID = "id";
    private final UserService userService;

    public FriendController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation(value = "닉네임으로 유저찾기", notes = "닉네임으로 다른 유저 찾기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "검색할 닉네임", paramType = "path", required = true)
    })
    @GetMapping(value = "/nickname/{nickname}")
    public ResponseEntity<String> searchUserByNickname(@PathVariable String nickname) {

            return ResponseEntity.status(HttpStatus.OK).body(userService.getIdByNickname(nickname));
    }

    @ApiOperation(value = "친구 등록하기", notes = "id로 내 친구 등록")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "등록할 친구 아이디", paramType = "path", required = true)
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> addFriend(@PathVariable String id, HttpServletRequest request) {
        String myId = request.getHeader(HEADER_ID);
        userService.addFriendById(myId, id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}