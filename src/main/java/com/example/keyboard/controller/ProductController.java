package com.example.keyboard.controller;

import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import com.example.keyboard.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    public final ProductService productService;

    // 상품 등록
    @PostMapping("/list/enroll")
    public ResponseEntity<Object> listEnroll(ProductEntity vo) {
        if (vo.getName() == null ||
                vo.getList_picture() == null ||
                vo.getList_back_picture() == null ||
                vo.getRepresent_picture() == null ||
                vo.getDesc_picture() == null) {
            return ResponseEntity.badRequest().body("파라미터를 확인해 주세요");
        }
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

    // 상세 상품 등록
    @PostMapping("/detail/enroll")
    public ResponseEntity<Object> insertProductDetail(ProductDetailEntity vo){
        try{
            productService.insertProductDetail(vo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //상세 상품 가져오기
    @GetMapping("/detail/get")
    public ResponseEntity<Object> selectProductDetailList(@RequestParam(value="product_id") Long productId){
        try{
            List<ProductDetailEntity> ProductDetailList = productService.selectProductDetailList(productId);
            return new ResponseEntity<>(ProductDetailList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 상세 상품 기본값( 0이면 1로 설정, 1이면 0으로 설정 )
    @PutMapping("/detail/default")
    public ResponseEntity<Object> setProductDetailDefault(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "product_detail_id")  Integer product_detail_id){
        try{
            productService.setProductDetailDefault(product_id, product_detail_id);
            return new ResponseEntity<>(product_id+"번 상품의 옵션 번호 "+product_detail_id+"의 상품 디폴트값 설정 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 상품 리스트 메인으로 설정
    @PutMapping("/list/setMain")
    public ResponseEntity<Object> setMainProduct(ProductEntity vo){
        try{
            productService.setMainProduct(vo);
            return new ResponseEntity<>("메인상품 등록 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}