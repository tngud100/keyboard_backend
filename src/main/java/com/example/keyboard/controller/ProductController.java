package com.example.keyboard.controller;

import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import com.example.keyboard.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "상품관련 API", description = "상품 CRUD API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    public final ProductService productService;

    @Operation(summary = "상품 등록")
    @PostMapping("/product/enroll")
    public ResponseEntity<Object> productEnroll(ProductEntity vo) {
        if (vo.getName() == null ||
                vo.getList_picture() == null ||
                vo.getList_back_picture() == null ||
                vo.getRepresent_picture() == null ||
                vo.getDesc_picture() == null) {
            return ResponseEntity.badRequest().body("파라미터를 확인해 주세요");
        }
        try {
            productService.productEnroll(vo);
            return new ResponseEntity<>("상품 등록 완료", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품 리스트 GET")
    @GetMapping("/product/get")
    public ResponseEntity<Object> selectProductList(){
        try{
            List<ProductEntity> ProductList =  productService.selectProductList();
            return new ResponseEntity<>(ProductList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품 수정", description = "상품 수정하기")
    @PutMapping("/product/{product_id}/update")
    public ResponseEntity<Object> updateProduct(ProductEntity vo){
        try{
            productService.updateProduct(vo);
            return new ResponseEntity<>("상품 수정 완료", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "상품 카테고리 등록", description = "상품 카테고리 등록")
    @PostMapping("/productcategory/enroll")
    public ResponseEntity<Object> enrollProductCategory(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "category_name") String category_name){
        try{
            productService.enrollProductCategory(product_id, category_name);
            return new ResponseEntity<>("상품 카테고리 등록 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "상세 상품 등록", description = "상세 상품 등록")
    @PostMapping("/productDetail/enroll")
    public ResponseEntity<Object> enrollProductDetail(ProductDetailEntity vo){
        try{
            productService.enrollProductDetail(vo);
            return new ResponseEntity<>("상세 상품 등록 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품의 상세 상품 리스트 GET", description = "상품의 상세 상품 가져오기")
    @GetMapping("/product/{product_id}/productDetail/get")
    public ResponseEntity<Object> selectProductDetailList(@PathVariable("product_id") Long product_id){
        try{
            List<ProductDetailEntity> ProductDetailList = productService.selectProductDetailList(product_id);
            return new ResponseEntity<>(ProductDetailList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "All 상세 상품 리스트 GET", description = "모든 상세 상품 가져오기")
    @GetMapping("productDetail/get")
    public ResponseEntity<Object> selectAllProductDetailList(){
        try{
            List<ProductDetailEntity> ProductDetailList = productService.selectAllProductDetailList();
            return new ResponseEntity<>(ProductDetailList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상세 상품 수정", description = "상세 상품 수정하기")
    @PutMapping("/productDetail/{product_detail_id}/update")
    public ResponseEntity<Object> updateProductDetail( ProductDetailEntity vo){
        try{
            productService.updateProductDetail(vo);
            return new ResponseEntity<>("상세 상품 수정 완료", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    @Operation(summary = "상품의 상세 상품 디폴트 값 설정", description = "상품의 상세 상품으로 상품의 기본가격에 포함( 0이면 1로 설정, 1이면 0으로 설정 )")
    @PutMapping("/productDetail/setProductDefault")
    public ResponseEntity<Object> setProductDetailDefault(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "product_detail_id")  Integer product_detail_id){
        try{
            productService.setProductDetailDefault(product_id, product_detail_id);
            return new ResponseEntity<>(product_id+"번 상품의 상세 상품 번호 "+product_detail_id+"이 디폴트값으로 설정 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "메인 페이지 상품 등록", description = "상품 리스트를 메인으로 설정하는 버튼")
    @PutMapping("/product/setMain")
    public ResponseEntity<Object> setMainProduct(ProductEntity vo){
        try{
            productService.setMainProduct(vo);
            return new ResponseEntity<>("메인상품 등록 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "메인 페이지 상품 리스트 GET", description = "메인으로 설정된 상품리스트 가져오기")
    @GetMapping("/product/getMain")
    public ResponseEntity<Object> selectMainProductList(){
        try{
            List<ProductEntity> vo = productService.selectMainProductList();
            return new ResponseEntity<>(vo, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}