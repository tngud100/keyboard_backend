package com.example.keyboard.entity.product;

import lombok.Data;

import java.text.DecimalFormat;
import java.time.LocalDate;

@Data
public class ProductEntity {
    private Long PRODUCT_ID;                // 상품 일련번호
    private String P_NAME;                  // 상품명
    private String P_MAIN_PICTURE;          // 메인 페이지 사진
    private String P_LIST_PICTURE;          // 목록 페이지 상품 사진
    private String P_LIST_BACK_PICTURE;     // 목록 페이지 상품 배경화면 사진
    private String P_DESCRIBE_PICTURE;      // 상품 설명 사진
    private DecimalFormat P_AMOUNT;         // 상품 금액
    private LocalDate MODIFIED_DATE;        // 수정일자
    private LocalDate CREATE_DATE;          // 등록일자
}