package com.example.keyboard.controller;

import com.example.keyboard.entity.jwt.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.keyboard.entity.user.userEntity;
import com.example.keyboard.service.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class userController {

    private final userService userService;
    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Object> join(userEntity vo){
        try {
            String userName = userService.join(vo);
            return new ResponseEntity<>(userName, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> loginForm, HttpServletRequest request){
        try{
            String loginId = loginForm.get("loginId");
            String password = loginForm.get("password");

//            JwtToken token = userService.login(loginId, password);

//            return ResponseEntity.ok(token);
            return new ResponseEntity<>( HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("fail",HttpStatus.BAD_REQUEST);
        }
    }
}
