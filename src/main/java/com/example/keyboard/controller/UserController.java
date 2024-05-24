package com.example.keyboard.controller;

import com.example.keyboard.entity.member.MemberEntity;
import com.example.keyboard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Frequency;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Tag(name = "유저관련 API", description = "회원가입/로그인, 휴대폰 번호 인증 등")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입")
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
    @Operation(summary = "로그아웃")
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

    @Operation(summary = "로그인 중복 확인")
    @GetMapping("/isIdDuplicateId")
    public ResponseEntity<Object> duplicateLoginId(@RequestParam(value = "Id") String Id){
        try {
            String isDuplicate = userService.duplicateLoginId(Id);
            return new ResponseEntity<>(isDuplicate, HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "휴대폰 인증번호 전송")
    @PostMapping("/send")
    public ResponseEntity<Object> sendVerifyNum(@RequestParam(value="phoneNum") String phoneNum){
        try {
            if(phoneNum.contains("-")){
                return new ResponseEntity<>("하이폰 제거 후 번호 다시 입력", HttpStatus.BAD_REQUEST);
            }
            userService.sendVerifyNum(phoneNum);
            return new ResponseEntity<>("인증번호 발송 완료", HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "휴대폰 인증번호 확인")
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

    @Operation(summary = "유저 권한 테스트 개발용")
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
