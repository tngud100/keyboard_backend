package com.example.keyboard.entity.Image.inquire;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class InquireImageEntity {
    private long inquires_id;
    private List<MultipartFile> pictures;
}
