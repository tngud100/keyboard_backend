package com.example.keyboard.entity.Image.product;

import lombok.Data;

@Data
public class ProductDaoEntity {
    private long img_id;
    private long product_id;
    private String img_name;
    private String img_path;
    private String img_type;
    private Long img_size;
}

