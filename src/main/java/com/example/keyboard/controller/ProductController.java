package com.example.keyboard.controller;

import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import com.example.keyboard.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    public final ProductService productService;

    // 상품 등록
    @PostMapping("/list/enroll")
    public ResponseEntity<Object> listEnroll(ProductEntity vo) {
        try {
            productService.listEnroll(vo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 상품 리스트 가져오기
    @GetMapping("/list/get")
    public ResponseEntity<Object> selectProductList(){
        try{
            List<ProductEntity> ProductList =  productService.selectProductList();
            return new ResponseEntity<>(ProductList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//
//    // 상세 상품 등록
//    @PostMapping("/detail/enroll")
//    public ResponseEntity<Object> insertProductDetail(ProductDetailEntity vo){
//        try{
//            productService.insertProductDetail(vo);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // 상세 상품 가져오기
//    @GetMapping("/detail/get")
//    public ResponseEntity<Object> selectProductDetailList(Integer product_id){
//        try{
//            productService.selectProductList(product_id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // 상세 상품 기본값
//    @PutMapping("/detail/default")
//    public ResponseEntity<Object> setProductDetailDefault(Integer product_id, Integer product_detail_id){
//        try{
//            productService.setProductDetailDefault(product_id, product_detail_id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // 상품 리스트 메인으로 설정
//    public ResponseEntity<Object> setMainProduct(ProductEntity vo){
//        try{
//            productService.setMainProduct(vo);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}