package com.na.backend.controller;

import com.na.backend.dto.UserDto;
import com.na.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping(value = "/user")
    public ResponseEntity<Map<String, String>> signUpUser(@RequestBody UserDto userDto, HttpServletResponse response){


        String token = userService.save(userDto);
        Map<String, String> tk = new HashMap<>();
        tk.put("token", token);
        return ResponseEntity.status(HttpStatus.OK).body(tk);


    }

    @GetMapping(value ="/login")
    public void logInUser(){


        //userService.login();
    }


}