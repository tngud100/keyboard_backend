package com.example.keyboard.controller;

import com.example.keyboard.entity.member.MemberEntity;
import com.example.keyboard.entity.product.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    // 메인 페이지 상품 등록
    @PostMapping("/main/enroll")
    public ResponseEntity<Object> mainProduct(ProductEntity vo) {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}