package com.example.keyboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class StatusCheckController {
    @GetMapping("/health")
    public ResponseEntity<String> checkHealthStatus() {
        try {
            System.out.println("check verify");
            return new ResponseEntity<>("check verify", HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/check-get")
    public ResponseEntity<String> check() {
        try {
            System.out.println("check");
            return new ResponseEntity<>("check", HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}