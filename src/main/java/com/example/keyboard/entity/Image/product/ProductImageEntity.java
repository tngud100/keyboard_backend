package com.example.keyboard.entity.Image.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductImageEntity {
    private long product_id;
    private String name;
    private MultipartFile represent_picture;
    private MultipartFile list_back_picture;
    private MultipartFile list_picture;
    private List<MultipartFile> desc_picture;
    private String product_type;
}
