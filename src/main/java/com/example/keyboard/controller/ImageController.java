package com.example.keyboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Tag(name = "이미지 API", description = "이미지 등록 API")
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${upload.path}") // application.properties에 설정된 이미지 업로드 경로를 가져옵니다.
    private String uploadPath;

    @Operation(summary = "이미지 업로드", description = "서버에 이미지 등록 후 경로를 return")
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            // 이미지를 지정된 경로에 저장합니다.
            String filePath = uploadPath + File.separator + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }

    @Operation(summary = "이미지 업로드 취소", description = "서버에 이미지 업로드를 취소합니다.")
    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancelUpload(@RequestParam("filePath") String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                return ResponseEntity.ok("File upload canceled successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to cancel file upload");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }

}
