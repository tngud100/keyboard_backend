package com.example.keyboard.controller;

import com.example.keyboard.entity.member.MemberEntity;
import com.example.keyboard.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;


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
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest Request){
        try {
            String AccessToken = Request.getHeader("Authorization");
            String RefreshToken = Request.getHeader("Refresh-Token");
            userService.logout(AccessToken, RefreshToken);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/check")
    public ResponseEntity<Object> check() {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            Iterator<? extends GrantedAuthority> iter = authorities.iterator();
            GrantedAuthority auth = iter.next();
            String role = auth.getAuthority();

            System.out.println(name);
            System.out.println(auth);
            System.out.println(role);

            return new ResponseEntity<>("토큰 확인",HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
