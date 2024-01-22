package com.example.keyboard.controller;

import com.example.keyboard.entity.member.memberEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/session")
    public ResponseEntity<Object> check() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        System.out.println(name);
        System.out.println(role);
        System.out.println(authentication);

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
