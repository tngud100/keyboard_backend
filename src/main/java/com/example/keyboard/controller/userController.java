package com.example.keyboard.controller;

import com.example.keyboard.entity.member.memberEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.keyboard.service.userService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class userController {

    private final userService userService;
    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<Object> join(memberEntity vo){
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
//            Map<String, String> TokenInfoBySecuruity = userService.storeUserInfo();
//            return new ResponseEntity<>(TokenInfoBySecuruity, HttpStatus.OK);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

//    @GetMapping("/login")
//    public ResponseEntity<Object> login() {
//        try {
//            userService.storeUserRole(userId, password);
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}
