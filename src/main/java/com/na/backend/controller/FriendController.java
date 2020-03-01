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

@Api(value="friend API")
@RestController
@RequestMapping("/friend")
public class FriendController {

    private static final String HEADER_ID = "id";
    private final UserService userService;

    public FriendController(UserService userService){
        this.userService = userService;
    }

//    @ApiOperation(value = "이름으로 유저찾기", notes = "이름으로 다른 유저 찾기")
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "name", value = "검색할 이름", paramType = "path", required = true)
//    })
//    @GetMapping(value = "/{name}")
//    public ResponseEntity<List<User>> searchUserByName(@PathVariable String name) {
//
//        return ResponseEntity.status(HttpStatus.OK).body(userService.findUsers(name));
//    }

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