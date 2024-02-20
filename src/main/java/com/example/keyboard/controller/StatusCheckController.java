package com.example.keyboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatusCheckController {
    @GetMapping("/")
    public String root() {
        return "index";
    }
    @GetMapping("/health")
    public ResponseEntity<Void> checkHealthStatus() {
        try {
            System.out.println("check verify");
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}