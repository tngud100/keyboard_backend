package com.example.keyboard.entity.product;

import lombok.Data;

@Data
public class ProductDetailEntity {
    private Long PRODUCT_DETAIL_ID;  // 상세 상품 일련번호
    private Long PRODUCT_ID;         // 상품 테이블 일련번호
    private int CATEGORY_STATE;      // 카테고리 상태     0: 기본값 X, 1: 기본값
    private String CATEGORY_NAME;    // 카테고리
    private String NAME;             // 이름
    private int AMOUNT;              // 금액
    private int DEFAULT_STATE;       // 기본값 상태      0: 기본값 X, 1: 기본값
    private int STOCK;               // 재고 수량
    private int FAULTY_STOCK;        // 불량 재고 수량
    private int SOLD_STOCK;          // 판매 수량
}

