package com.example.keyboard.repository.product;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductEntity {
    private Long product_id;              // 상품 일련번호
    private Long product_detail_id;       // 상품 일련번호
    private String name;                  // 상품명
    private String main_picture;          // 메인 페이지 사진
    private String represent_picture;     // 대표 사진
    private String list_picture;          // 목록 페이지 상품 사진
    private String list_back_picture;     // 목록 페이지 상품 배경화면 사진
    private String desc_picture;          // 상품 설명 사진
    private int main_pic_state;           // 메인 사진 등록상태   0: 메인 페이지, 1: 대표사진
    private int amount;                   // 상품 금액       productDetail에서 product_id를 가지고 있는 가격을 모두 더한 값이다
    private LocalDate modified_date;      // 수정일자
    private LocalDate create_date;        // 등록일자
}