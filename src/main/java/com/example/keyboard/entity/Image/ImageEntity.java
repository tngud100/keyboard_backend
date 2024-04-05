package com.example.keyboard.entity.Image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ImageEntity {
    private long id;
    private long product_id;
    private String img_name;
    private String img_path;
    private String img_type;
    private Long img_size;
}
