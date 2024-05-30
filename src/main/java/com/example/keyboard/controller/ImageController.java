    package com.example.keyboard.controller;

    import com.example.keyboard.entity.Image.inquire.InquireDaoEntity;
    import com.example.keyboard.entity.Image.inquire.InquireImageEntity;
    import com.example.keyboard.entity.Image.product.ProductDaoEntity;
    import com.example.keyboard.entity.Image.product.ProductImageEntity;
    import com.example.keyboard.service.ImgUploadService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.ArrayList;
    import java.util.List;

    @Tag(name = "이미지 API", description = "이미지 등록 API")
    @Component
    public class ImageController {

        private final ImgUploadService imgUploadService;

        public ImageController(ImgUploadService imgUploadService) {
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
                    ProductDaoEntity imgEntity = imgUploadService.uploadImg(picture.get(i), product_id);
                    imgUploadService.saveImgPath(imgEntity);
                }
                for( MultipartFile desc_pic : desc_picture){
                    ProductDaoEntity imgEntity = imgUploadService.uploadImg(desc_pic, product_id);
                    imgUploadService.saveImgPath(imgEntity);
                }

                return new ResponseEntity<>("이미지 업로드 완료", HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        @Operation(summary = "이미지 업로드")
        public ResponseEntity<Object> uploadInquireImg(List<MultipartFile> multipartFileList, Long inquires_id) throws Exception {
            try{
                for(MultipartFile imgFile : multipartFileList){
                    InquireDaoEntity inquireDaoEntity = imgUploadService.uploadInquireImg(imgFile, inquires_id);
                    imgUploadService.saveInquireImgPath(inquireDaoEntity);
                }
                return new ResponseEntity<>("이미지 업로드 완료", HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        public ResponseEntity<Object> uploadMainImg(MultipartFile mainImg, @RequestParam("product_id") Long product_id) throws Exception{
            try {
                ProductDaoEntity imgEntity = imgUploadService.uploadImg(mainImg, product_id);
                imgUploadService.saveImgPath(imgEntity);
                return new ResponseEntity<>("이미지 업로드 완료", HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(),  HttpStatus.BAD_REQUEST);
            }
        }
        @Operation(summary = "이미지 삭제")
        public ResponseEntity<String> deleteImg(@RequestParam("product_id") Long product_id) throws Exception {
            try{
                imgUploadService.deleteImg(product_id);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        @Operation(summary = "메인 이미지 화보 삭제")
        public ResponseEntity<String> deleteMainImg(@RequestParam("product_id") Long product_id) throws Exception {
            try{
                imgUploadService.deleteMainImg(product_id);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

        public ResponseEntity<Object> modifyInquireImg(List<MultipartFile> multipartFileList, Long inquires_id) throws Exception{
            try{
                for(MultipartFile imgFile : multipartFileList){

                }
                return new ResponseEntity<>("이미지 수정 완료", HttpStatus.OK);
            }catch(Exception e) {
                System.out.println(e.getMessage());
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
    }

