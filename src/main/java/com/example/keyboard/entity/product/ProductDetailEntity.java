package com.example.keyboard.entity.product;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class ProductDetailEntity {
    private Long PRODUCT_DETAIL_ID;     // 상세 상품 일련번호
    private Long PRODUCT_ID;            // 상품 일련번호
    private int PD_CATEGORY;            // 카테고리
    private String PD_NAME;             // 이름
    private DecimalFormat PD_AMOUNT;    // 금액
    private int PD_DEFAULT_STATE;       // 기본값 상태
    private int PD_STOCK;               // 재고 수량
    private int PD_FAULTY_STOCK;        // 불량 재고 수량
    private int PD_SOLD_STOCK;          // 판매 수량
}

