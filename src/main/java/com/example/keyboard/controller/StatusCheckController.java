package com.example.keyboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StatusCheckController {
    @GetMapping("/")
    public ResponseEntity<Void> checkHealthStatus() {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}