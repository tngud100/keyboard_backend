package com.example.keyboard.entity.product;

import lombok.Data;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Data
public class ProductEntity {
    private Long PRODUCT_ID;              // 상품 일련번호
    private Long PRODUCT_DETAIL_ID;       // 상품 일련번호
    private String NAME;                  // 상품명
    private String MAIN_PICTURE;          // 메인 페이지 사진
    private String REPRESENT_PICTURE;     // 대표 사진
    private String LIST_PICTURE;          // 목록 페이지 상품 사진
    private String LIST_BACK_PICTURE;     // 목록 페이지 상품 배경화면 사진
    private String DESC_PICTURE;          // 상품 설명 사진
    private int MAIN_PIC_STATE;           // 메인 사진 등록상태   0: 메인 페이지, 1: 대표사진
    private int AMOUNT;                   // 상품 금액       productDetail에서 product_id를 가지고 있는 가격을 모두 더한 값이다
    private LocalDate MODIFIED_DATE;      // 수정일자
    private LocalDate CREATE_DATE;        // 등록일자
}