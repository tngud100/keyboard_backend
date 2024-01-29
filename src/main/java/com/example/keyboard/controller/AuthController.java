package com.example.keyboard.controller;

import com.example.keyboard.entity.member.MemberEntity;
import com.example.keyboard.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService userService;

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
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest Request){
        try {
            String AccessToken = Request.getHeader("Authorization");
            String RefreshToken = Request.getHeader("Refresh-Token");
            String AccessTokenExceptBeerer = AccessToken.split(" ")[1];
            userService.logout(AccessTokenExceptBeerer, RefreshToken);
            System.out.println("로그아웃 완료");
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/send")
    public ResponseEntity<Object> sendVerifyNum(@RequestParam(value="phoneNum") String phoneNum){
        try {
            if(phoneNum.contains("-")){
                return new ResponseEntity<>("하이폰 제거 후 번호 다시 입력", HttpStatus.OK);
            }
            userService.sendVerifyNum(phoneNum);
            return new ResponseEntity<>("인증번호 발송 완료", HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/verify")
    public ResponseEntity<Object> checkVerifyNum(@RequestParam(value="phoneNum") String phoneNum, @RequestParam(value="verifyNum") String varifyNum){
        try {
            boolean check = userService.checkVerifyNum(phoneNum, varifyNum);
            return new ResponseEntity<>(check, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/check")
    public ResponseEntity<Object> check(HttpServletRequest Request) {
        try {
            String AccessToken = Request.getHeader("Authorization");
            System.out.println("왜 들어와지냐");
            Map<String, String> check = userService.check(AccessToken);

            return new ResponseEntity<>(check, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
