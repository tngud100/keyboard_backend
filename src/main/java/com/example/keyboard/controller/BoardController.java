package com.example.keyboard.controller;

import com.example.keyboard.service.BoardService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
@RequestMapping("/data")
public class BoardController {

    private final BoardService boardService;
    private String data;

    @GetMapping
    @ResponseBody
    public String hi(){
        data = boardService.findData();
        return data;
    }
}
