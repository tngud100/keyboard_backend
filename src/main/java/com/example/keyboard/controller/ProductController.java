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

    /////////////////////////////////// POST ///////////////////////////////////
    @Operation(summary = "상품 등록")
    @PostMapping("/product/enroll")
    public ResponseEntity<Object> productEnroll(ProductEntity vo) {
        // 중복된 상품명 검사 및 이미지 업로드 경로 가져오기
        if (vo.getName() == null ||
                vo.getList_picture() == null ||
                vo.getList_back_picture() == null ||
                vo.getRepresent_picture() == null ||
                vo.getDesc_picture() == null) {
            return ResponseEntity.badRequest().body("모두 입력해 주세요");
        }
        // 중복된 상품명 검사
//        if (productService.isProductExists(vo.getName())) {
//            return ResponseEntity.badRequest().body("이미 존재하는 상품입니다.");
//        }

        try {
            productService.productEnroll(vo);
            return new ResponseEntity<>("상품 등록 완료", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품 카테고리 등록", description = "상품 카테고리 등록(최초 등록은 기본값 설정)")
    @PostMapping("/productcategory/enroll")
    public ResponseEntity<Object> enrollProductCategory(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "category_name") String category_name, @RequestParam(value = "category_state") int category_state){
        // 해당 상품의 카테고리를 검색하고 카테고리가 없을 시 자동으로 category_state가 1로 지정
        try{
            productService.enrollProductCategory(product_id, category_name, category_state);
            return new ResponseEntity<>("상품 카테고리 등록 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상세 상품 등록", description = "상세 상품 등록")
    @PostMapping("/productDetail/enroll")
    public ResponseEntity<Object> enrollProductDetail(ProductDetailEntity vo){
        // 상세 상품 모든 리스트를 검색하여 현재 등록하는 상세 상품 명의 이름과 같을시에 error 반환
        // category_state의 값을 확인하고 1일시에는 default_state를 1로 설정 후 등록 로직 추가
        // default_state가 1인지 확인 하고 productEntity의 amount에 ProductDetailEntity의 amount를 추가
        try{
            productService.enrollProductDetail(vo);
            return new ResponseEntity<>("상세 상품 등록 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    /////////////////////////////////// PUT ///////////////////////////////////
    @Operation(summary = "상품 수정", description = "상품 수정하기")
    @PutMapping("/product/{product_id}/update")
    public ResponseEntity<Object> updateProduct(ProductEntity vo) {
        // 기존과 데이터가 다른 데이터가 확인 될 시에 데이터 베이스에 Update
        try {
            productService.updateProduct(vo);
            return new ResponseEntity<>("상품 수정 완료", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품 카테고리 수정", description = "상품 카테고리 수정하기")
    @PutMapping("/product/{product_id}/category/update")
    public ResponseEntity<Object> updateProductCategory(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "product_category_id") Long product_category_id,
                                                        @RequestParam(value = "category_name") String category_name, @RequestParam(value = "category_state") int category_state){
        // category_state가 1에서 0으로 바뀔시에 해당 상품의 카테고리를 모두 검색하여 다른 카테고리의 state가 1이 없을 경우 error 반환
        // 같은 이름을 가진 카테고리 입력시에 error 반환
        // 기존과 데이터가 다른 데이터가 확인 될 시에 데이터 베이스에 Update
        try{
            productService.updateProductCategory(product_id, product_category_id, category_name, category_state);
            return new ResponseEntity<>("상품 카테고리 수정 완료", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상세 상품 수정", description = "상세 상품 수정하기")
    @PutMapping("/productDetail/{product_detail_id}/update")
    public ResponseEntity<Object> updateProductDetail(ProductDetailEntity vo){
        // 같은 이름을 가진 상세 상품 입력시에 error 반환
        // default_state가 0으로 바뀔 시 해당 상품의 카테고리의 state가 1일 경우 같은 조건인 상세상품을 모두 검색하고 default_state가 1인 상세 상품이 없을 시 error반환
        // default_state가 0으로 바뀔 시 위의 로직이 통과시에 해당 상품의 amount값에서 상세 상품의 amount를 뺀 값을 데이터베이스에 저장
        // 기존과 데이터가 다른 데이터가 확인 될 시에 데이터 베이스에 Update
        try{
            productService.updateProductDetail(vo);
            return new ResponseEntity<>("상세 상품 수정 완료", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "메인 페이지 상품 설정", description = "상품 리스트를 메인으로 설정하는 버튼")
    @PutMapping("/product/setMain")
    public ResponseEntity<Object> setMainProduct(ProductEntity vo){
        try{
            productService.setMainProduct(vo);
            return new ResponseEntity<>("메인상품 설정 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품의 상세 상품 디폴트 값 설정", description = "상품의 상세 상품으로 상품의 기본가격에 포함( 0이면 1로 설정, 1이면 0으로 설정 )")
    @PutMapping("/productDetail/setProductDefault")
    public ResponseEntity<Object> setProductDetailDefault(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "product_detail_id") Long product_detail_id){
        try{
            productService.setProductDetailDefault(product_id, product_detail_id);
            return new ResponseEntity<>(product_id+"번 상품의 상세 상품 번호 "+product_detail_id+"이 디폴트값으로 설정 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품의 카테고리 디폴트 값 설정", description = "상품의 카테고리 기본값 설정( 0이면 1로 설정, 1이면 0으로 설정 )")
    @PutMapping("/productDetail/setCategoryDefault")
    public ResponseEntity<Object> setProductCategoryDefault(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "product_category_id") Long product_category_id){
        try{
            productService.setProductCategoryDefault(product_id, product_category_id);
            return new ResponseEntity<>(product_id+"번 상품의 카테고리 번호 "+product_category_id+"이 디폴트값으로 설정 완료", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    /////////////////////////////////// GET ///////////////////////////////////
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