package com.example.keyboard.entity.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductEntity {
    private Long product_id;              // 상품 일련번호
    private Long product_detail_id;       // 상품 일련번호
    private String name;                  // 상품명
    private String main_picture;          // 메인 페이지 사진
    private String main_picture_name;     // 사진 이름
    private String represent_picture;     // 대표 사진
    private String represent_picture_name; // 사진 이름
    private String list_picture;          // 목록 페이지 상품 사진
    private String list_picture_name;      // 사진 이름
    private String list_back_picture;     // 목록 페이지 상품 배경화면 사진
    private String list_back_picture_name; // 사진 이름
    private List<String> desc_picture = new ArrayList<>();          // 상품 설명 사진
    private List<String> desc_picture_name = new ArrayList<>();          // 사진 이름
    private int main_pic_state;           // 메인 사진 등록상태   0: 메인 페이지, 1: 대표사진
    private int amount;                   // 상품 금액       productDetail에서 product_id를 가지고 있는 가격을 모두 더한 값이다
    private LocalDate modified_date;      // 수정일자
    private LocalDate create_date;        // 등록일자
    private String type;                  // 종류

    public Object getFieldValue(String field) {
        switch (field) {
            case "name":
                return getName();
            case "represent_picture":
                return getRepresent_picture();
            case "represent_picture_name":
                return getRepresent_picture_name();
            case "list_picture":
                return getList_picture();
            case "list_picture_name":
                return getList_picture_name();
            case "list_back_picture":
                return getList_back_picture();
            case "list_back_picture_name":
                return getList_back_picture_name();
            case "desc_picture":
                return getDesc_picture();
            case "desc_picture_name":
                return getDesc_picture_name();
            case "type":
                return getType();
            default:
                return null; // 필드 이름이 잘못된 경우 null 반환
        }
    }

    public void setFieldValue(String field, Object value) {
        switch (field) {
            case "name":
                setName((String) value);
                break;
            case "represent_picture":
                setRepresent_picture((String) value);
                break;
            case "represent_picture_name":
                setRepresent_picture_name((String) value);
                break;
            case "list_picture":
                setList_picture((String) value);
                break;
            case "list_picture_name":
                setList_picture_name((String) value);
                break;
            case "list_back_picture":
                setList_back_picture((String) value);
                break;
            case "list_back_picture_name":
                setList_back_picture_name((String) value);
                break;
            case "desc_picture":
                getDesc_picture().add((String) value);
                break;
            case "desc_picture_name":
                getDesc_picture_name().add((String) value);
                break;
            case "type":
                setType((String) value);
                break;
            default:
                // 필드 이름이 잘못된 경우 아무것도 하지 않음
                break;
        }
    }
}