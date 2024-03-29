package com.example.keyboard.entity.product;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductDetailEntity {
    private Long product_detail_id;  // 상세 상품 일련번호
    private Long product_id;         // 상품 테이블 일련번호
    private Long product_category_id;        // 카테고리 일련번호
    private String category_name;      // 카테고리 이름
    private int category_state;     // 카테고리 기본값        0: 기본값 X(default_state가 설정되지 않은 상세상품을 가져도 된다),
                                                        // 1: 기본값(default_state가 1인 상세상품을 최소 하나는 가져야 한다 )
    private String name;             // 이름
    private int amount;              // 금액
    private int default_state;       // 기본값 상태      0: 기본값 X, 1: 기본값
    private int stock;               // 재고 수량
    private int faulty_stock;        // 불량 재고 수량
    private int sold_stock;          // 판매 수량
    private LocalDate modified_date; // 수정일자
    private LocalDate create_date;   // 등록일자
}