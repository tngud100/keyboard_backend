package com.example.keyboard.controller;

import com.example.keyboard.config.JWT.JWTUtil;
import com.example.keyboard.config.Redis.RedisUtils;
import com.example.keyboard.entity.Auth.LoginRequest;
import com.example.keyboard.service.AuthService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api")
public class AuthController {
    private final JWTUtil jwtUtil;
    private final AuthService authService;
    @Autowired
    public AuthController(JWTUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @GetMapping("/auth/validateToken")
    public ResponseEntity<String> validateToken(HttpServletRequest request) throws Exception {
        String authorization= request.getHeader("Authorization");
        String refreshToken= request.getHeader("Refresh-Token");

        if (authorization == null || refreshToken == null) {
            return new ResponseEntity<>("인증 정보가 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        String accessToken = authorization.split(" ")[1];
        try {
            boolean isBlackList = authService.checkBlackList(accessToken);
            if(isBlackList){
               return new ResponseEntity<>("이미 로그아웃된 토큰입니다.", HttpStatus.UNAUTHORIZED);
            }

            boolean isAccessTokenExpired = authService.isExpiredAccessToken(accessToken); // access토큰 유효성 검사
            if(!isAccessTokenExpired){ // access토큰 만료 후 refresh토큰을 통해 access토큰 재발급
              accessToken = authService.newAccessTokenByRefreshToken(refreshToken);
            }

            authService.setSecurityAuthenticationToken(accessToken); // 스프링 시큐리티에 사용자 등록

            String user_role = jwtUtil.getRole(accessToken);

            return new ResponseEntity<>(user_role, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),  HttpStatus.UNAUTHORIZED);
        }

    }
}
