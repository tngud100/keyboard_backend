package com.example.keyboard.controller;

import com.example.keyboard.entity.Image.ImageEntity;
import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import com.example.keyboard.entity.Image.ProductImageEntity;
import com.example.keyboard.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.server.PathParam;
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
    public final ImageController imgController;

    /////////////////////////////////// POST ///////////////////////////////////
    @Operation(summary = "상품 등록")
    @PostMapping("/product/enroll")
    public ResponseEntity<Object> productEnroll(ProductImageEntity vo) {
        try {
            // 중복된 상품명 검사 및 이미지 업로드 경로 가져오기
            if (vo.getName() == null ||
                    vo.getList_picture() == null ||
                    vo.getList_back_picture() == null ||
                    vo.getRepresent_picture() == null ||
                    vo.getDesc_picture() == null) {
                return ResponseEntity.badRequest().body("모두 기입해 주세요");
            }

            if (productService.isProductNameExists(vo.getName())) {
                return ResponseEntity.badRequest().body("이미 존재하는 상품입니다.");
            }
            String name = vo.getName();
            String type = vo.getProduct_type();

            Long product_id = productService.productEnroll(name, type);
            vo.setProduct_id(product_id);
            imgController.uploadImage(vo);
            return new ResponseEntity<>("상품 등록 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품 카테고리 등록", description = "상품 카테고리 등록(최초 등록은 기본값 설정)")
    @PostMapping("/productcategory/enroll")
    public ResponseEntity<Object> enrollProductCategory(@RequestParam(value="product_id") Long product_id, @RequestParam(value="category_name") String category_name, @RequestParam(value="category_state") int category_state){
        try{
            if (productService.isCategoryNameExists(product_id, category_name)) {
                return ResponseEntity.badRequest().body("이미 존재하는 카테고리입니다.");
            }
            productService.enrollProductCategory(product_id, category_name, category_state);
            return new ResponseEntity<>("상품 카테고리 등록 완료", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상세 상품 등록", description = "상세 상품 등록")
    @PostMapping("/productDetail/enroll")
    public ResponseEntity<Object> enrollProductDetail(ProductDetailEntity vo){
        try{
            if (productService.isDetailNameExists(vo.getProduct_id(), vo.getName())) {
                return ResponseEntity.badRequest().body("해당 상품에 이미 존재하는 상세 상품입니다.");
            }
            productService.enrollProductDetail(vo);
            return new ResponseEntity<>("상세 상품 등록 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



    /////////////////////////////////// PUT ///////////////////////////////////
    @Operation(summary = "상품 수정", description = "상품 수정하기")
    @PutMapping("/product/{product_id}/update")
    public ResponseEntity<Object> updateProduct(ProductImageEntity vo) {
        try {
            if (!vo.getName().equals(productService.selectProductName(vo.getProduct_id())) &&
                    productService.isProductNameExists(vo.getName())) {
                return ResponseEntity.badRequest().body("이미 존재하는 상품입니다.");
            }
            productService.updateProduct(vo);
            return new ResponseEntity<>("상품 수정 완료", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "상품 카테고리 수정", description = "상품 카테고리 수정하기")
    @PutMapping("/product/{product_id}/category/update")
    public ResponseEntity<Object> updateProductCategory(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "product_category_id") Long product_category_id,
                                                        @RequestParam(value = "category_name") String category_name){
        try{
            if (!category_name.equals(productService.selectCategoryName(product_category_id)) &&
                    productService.isCategoryNameExists(product_id, category_name)) {
                return ResponseEntity.badRequest().body("이미 존재하는 카테고리입니다.");
            }
            productService.updateProductCategory(product_id, product_category_id, category_name);
            return new ResponseEntity<>("상품 카테고리 수정 완료", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("시스템 오류. 개발자에게 문의 하세요", HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상세 상품 수정", description = "상세 상품 수정하기")
    @PutMapping("/productDetail/{product_detail_id}/update")
    public ResponseEntity<Object> updateProductDetail(ProductDetailEntity vo){
        // 기존과 데이터가 다른 데이터가 확인 될 시에 데이터 베이스에 Update
        try{
            if (!vo.getName().equals(productService.selectProductDetailName(vo.getProduct_detail_id())) &&
                    productService.isDetailNameExists(vo.getProduct_id(), vo.getName())) {
                return ResponseEntity.badRequest().body("해당 상품에 이미 존재하는 상세 상품입니다.");
            }
            productService.updateProductDetail(vo);
            return new ResponseEntity<>("상세 상품 수정 완료", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>("시스템 오류. 개발자에게 문의 하세요", HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "메인 페이지 상품 설정", description = "상품 리스트를 메인으로 설정하는 버튼")
    @PutMapping("/product/setMain")
    public ResponseEntity<Object> setMainProduct(ProductEntity vo){
        try{
            productService.setMainProduct(vo);
            return new ResponseEntity<>("메인상품 설정 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("시스템 오류. 개발자에게 문의 하세요", HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품의 상세 상품 디폴트 값 설정", description = "상품의 상세 상품으로 상품의 기본가격에 포함( 0이면 1로 설정, 1이면 0으로 설정 ), 카테고리 기본값이 1일때 기본값 선택해제 누를시 error반환")
    @PutMapping("/productDetail/setProductDefault")
    public ResponseEntity<Object> setProductDetailDefault(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "product_detail_id") Long product_detail_id){
        try{
            productService.setProductDetailDefault(product_id, product_detail_id);
            return new ResponseEntity<>(product_id+"번 상품의 상세 상품 번호 "+product_detail_id+"이 상태 업데이트 완료", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품의 카테고리 디폴트 값 설정", description = "상품의 카테고리 기본값 설정( 0이면 1로 설정, 1이면 0으로 설정 )")
    @PutMapping("/productDetail/setCategoryDefault")
    public ResponseEntity<Object> setProductCategoryDefault(@RequestParam(value = "product_id") Long product_id, @RequestParam(value = "product_category_id") Long product_category_id){
        try{
            productService.setProductCategoryDefault(product_id, product_category_id);
            return new ResponseEntity<>(product_id+"번 상품의 카테고리 번호 "+product_category_id+"이 상태 업데이트 완료", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "상품 리스트 GET")
    /////////////////////////////////// GET ///////////////////////////////////
    @GetMapping("/product/get")
    public ResponseEntity<Object> selectProductList(){
        try{
            List<ProductEntity> ProductList =  productService.selectProductList();
            for(ProductEntity product : ProductList){
                Long product_id = product.getProduct_id();
                List<ImageEntity> ImageList = productService.selectProductImgList(product_id);
                for(ImageEntity productImg : ImageList){
                    String Img_type = productImg.getImg_type();
                    String Img_path = productImg.getImg_path();
                    String Img_name = productImg.getImg_name();

                    if(Img_type.equals("represent_picture")){
                        product.setRepresent_picture(Img_path);
                        product.setRepresent_picture_name(Img_name);
                    }else if(Img_type.equals("list_picture")){
                        product.setList_picture(Img_path);
                        product.setList_picture_name(Img_name);
                    }else if(Img_type.equals("list_back_picture")){
                        product.setList_back_picture(Img_path);
                        product.setList_back_picture_name(Img_name);
                    }else if(Img_type.equals("desc_picture")){
                        product.getDesc_picture().add(Img_path);
                        product.getDesc_picture_name().add(Img_name);
                    }
                }
            }
            return new ResponseEntity<>(ProductList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품의 상세 상품 리스트 GET", description = "상품의 상세 상품 가져오기")
    @GetMapping("/product/{product_id}/productDetail/get")
    public ResponseEntity<Object> selectProductDetailList(@PathVariable("product_id") Long product_id){
        try{
            List<ProductDetailEntity> ProductDetailList = productService.selectProductDetailList(product_id);
            return new ResponseEntity<>(ProductDetailList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품의 카테고리 리스트 GET", description = "상품의 카테고리 가져오기")
    @GetMapping("/product/{product_id}/category/get")
    public ResponseEntity<Object> selectProductCategoryList(@PathVariable("product_id") Long product_id){
        try{
            List<ProductDetailEntity> ProductCategoryList = productService.selectProductCategoryList(product_id);
            return new ResponseEntity<>(ProductCategoryList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "상품의 카테고리별 상세상품 리스트 GET", description = "상품의 카테고리별 상세상품 가져오기")
    @GetMapping("/product/{product_id}/category/{product_category_id}/get")
    public ResponseEntity<Object> selectProductCategoryDetailList(@PathVariable("product_id") Long product_id, @PathVariable("product_category_id") Long product_category_id){
        try{
            List<ProductDetailEntity> ProductCategoryDetailList = productService.selectProductCategoryDetailList(product_id, product_category_id);
            return new ResponseEntity<>(ProductCategoryDetailList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "All 상세 상품 리스트 GET", description = "모든 상세 상품 가져오기")
    @GetMapping("productDetail/get")
    public ResponseEntity<Object> selectAllProductDetailList(){
        try{
            List<ProductDetailEntity> ProductDetailList = productService.selectAllProductDetailList();
            return new ResponseEntity<>(ProductDetailList, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Operation(summary = "메인 페이지 상품 리스트 GET", description = "메인으로 설정된 상품리스트 가져오기")
    @GetMapping("/product/getMain")
    public ResponseEntity<Object> selectMainProductList(){
        try{
            List<ProductEntity> vo = productService.selectMainProductList();
            return new ResponseEntity<>(vo, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    /////////////////////////////////// delete ///////////////////////////////////
    // JWTFilter 오류 발생 controll 확인하기
    // 200 ok 떠도 데이터 베이스 그대로


    // 연결 고리 : 상품 > 상품 카테고리 > 상세 상품

    // 상품 삭제
    // 1. 상품 삭제
    @Operation(summary = "상품삭제", description = "해당 상품 삭제하기(해당 상품에 관련된 카테고리 및 상세상품도 같이 삭제)")
    @DeleteMapping("/product/{product_id}/delete")
    public ResponseEntity<Object> deleteProduct(@PathVariable("product_id") Long product_id){
        try{
            productService.deleteProduct(product_id);
            return new ResponseEntity<>(product_id + "번 상품, 이에 해당되는 카테고리, 상세상품  모두 삭제", HttpStatus.OK);
        } catch (Exception e){
            // 예외 로깅
            System.out.println(e.getMessage());
            return new ResponseEntity<>("상품 삭제 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 2. 상품 카테고리 삭제
    @Operation(summary = "상품 카테고리 삭제", description = "해당 카테고리 삭제하기(해당 카테고리에 관련된 상세상품도 같이 삭제)")
    @DeleteMapping("/productCategory/{product_category_id}/delete")
    public ResponseEntity<Object> deleteProductCategory(@PathVariable("product_category_id") Long product_category_id){
        try{
            productService.deleteProductCategory(product_category_id);
            return new ResponseEntity<>(product_category_id + "번 카테고리, 이에 해당하는 상세상품 모두 삭제", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 상세 상품 삭제
    // 1. 상세 상품 삭제
    @Operation(summary = "상세 상품 삭제", description = "해당 상세 상품 삭제하기")
    @DeleteMapping("/productDetail/{product_detail_id}/delete")
    public ResponseEntity<Object> deleteProductDetail(@PathVariable("product_detail_id") Long product_detail_id){
        try{
            productService.deleteProductDetail(product_detail_id);
            return new ResponseEntity<>(product_detail_id + "번 상세 상품 삭제", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}