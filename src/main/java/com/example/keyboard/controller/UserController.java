package com.example.keyboard.controller;

import com.example.keyboard.entity.member.MemberEntity;
import com.example.keyboard.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Object> join(MemberEntity vo){
        try {
            String result = userService.join(vo);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/login")
//    public ResponseEntity<Object> login(@RequestBody String loginId, @RequestBody String password){
//        try {
//            Map<String, String> TokenInfoBySecuruity = UserService.storeUserInfo();
//            return new ResponseEntity<>(TokenInfoBySecuruity, HttpStatus.OK);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/check")
    public ResponseEntity<Object> login() {
        try {
            System.out.println("토큰 확인");
            return new ResponseEntity<>("토큰 확인",HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
