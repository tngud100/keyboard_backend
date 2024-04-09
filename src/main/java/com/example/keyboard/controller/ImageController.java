    package com.example.keyboard.controller;

    import com.example.keyboard.entity.Image.ImageEntity;
    import com.example.keyboard.entity.Image.ProductImageEntity;
    import com.example.keyboard.entity.product.ProductEntity;
    import com.example.keyboard.repository.ProductDao;
    import com.example.keyboard.service.ImgUploadService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.File;
    import java.util.ArrayList;
    import java.util.List;

    @Tag(name = "이미지 API", description = "이미지 등록 API")
    @Component
    public class ImageController {

        private final ImgUploadService imgUploadService;

        public ImageController(ProductDao productDao, ImgUploadService imgUploadService) {
            this.imgUploadService = imgUploadService;
        }

        @Operation(summary = "이미지 업로드")
        public ResponseEntity<Object> uploadImage(ProductImageEntity productImageEntity) throws Exception {
            try{
                Long product_id = productImageEntity.getProduct_id();
                MultipartFile list_picture = productImageEntity.getList_picture();
                MultipartFile represent_picture = productImageEntity.getRepresent_picture();
                MultipartFile list_back_picture = productImageEntity.getList_back_picture();
                List<MultipartFile> desc_picture = productImageEntity.getDesc_picture();

                List<MultipartFile> picture = new ArrayList<>();
                picture.add(list_picture);
                picture.add(represent_picture);
                picture.add(list_back_picture);

                for(int i = 0; i < picture.size(); i ++){
                    ImageEntity imgEntity = imgUploadService.uploadImg(picture.get(i), product_id);
                    imgUploadService.saveImgPath(imgEntity);
                }
                for( MultipartFile desc_pic : desc_picture){
                    ImageEntity imgEntity = imgUploadService.uploadImg(desc_pic, product_id);
                    imgUploadService.saveImgPath(imgEntity);
                }

                return new ResponseEntity<>("이미지 업로드 완료", HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        @Operation(summary = "이미지 업로드 취소")
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

        public ResponseEntity<Object> modifyUploadImg(ProductImageEntity productImageEntity) throws Exception{
            try{
                imgUploadService.modifyUpload(productImageEntity);
                return new ResponseEntity<>("이미지 수정 완료", HttpStatus.OK);
            }catch(Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

