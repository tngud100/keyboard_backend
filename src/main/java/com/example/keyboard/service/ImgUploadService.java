package com.example.keyboard.service;

import com.example.keyboard.entity.Image.inquire.InquireDaoEntity;
import com.example.keyboard.entity.Image.product.ProductDaoEntity;
import com.example.keyboard.entity.Image.product.ProductImageEntity;
import com.example.keyboard.entity.product.ProductEntity;
import com.example.keyboard.repository.ImageDao;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImgUploadService {

    @Value("${upload.path}") // application.properties에 설정된 이미지 업로드 경로를 가져옵니다.
    private String uploadPath;

    private final ImageDao imageDao;
    private ProductDaoEntity fileEntity;
    private final com.example.keyboard.repository.ProductDao productDao;

    public ProductDaoEntity uploadImg(MultipartFile multipartFile, Long product_id) throws Exception {

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if (multipartFile.isEmpty()) {
            throw new Exception("파일이 비어있습니다.");
        }

        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() +uploadPath;
//        String absolutePath = new File("").getAbsolutePath() + "\\" +uploadPath;

        // 경로를 지정하고 그곳에다가 저장
        File file = new File(absolutePath);
        // 저장할 위치의 디렉토리가 존지하지 않을 경우
        if (!file.exists()) {
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }

        // 파일이 비어 있지 않을 때 작업을 시작해야 오류가 나지 않는다
        if (!multipartFile.isEmpty()) {
            // jpeg, png, gif 파일들만 받아서 처리할 예정
            String contentType = multipartFile.getContentType();
            String originalFileExtension;
            // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("잘못된 파일 입니다.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if (contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                }
                // 다른 파일 명이면 아무 일 하지 않는다
                else {
                    throw new Exception("jpg, png, gif만 업로드 가능합니다.");
                }
            }
            // 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
            String new_file_name = System.nanoTime() + originalFileExtension;

            fileEntity = new ProductDaoEntity();
            fileEntity.setProduct_id(product_id);
            fileEntity.setImg_name(multipartFile.getOriginalFilename());
            fileEntity.setImg_path("/images" + File.separator + new_file_name);
            fileEntity.setImg_size(multipartFile.getSize());
            fileEntity.setImg_type(multipartFile.getName());

            // 저장된 파일로 변경하여 이를 보여주기 위함
            file = new File(absolutePath + File.separator + new_file_name);
            multipartFile.transferTo(file);
        }
        return fileEntity;
    }
    public InquireDaoEntity uploadInquireImg(MultipartFile multipartFile, Long inquires_id) throws Exception{

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if (multipartFile.isEmpty()) {
            throw new Exception("파일이 비어있습니다.");
        }

        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() +uploadPath;
//        String absolutePath = new File("").getAbsolutePath() + "\\" +uploadPath;

        // 경로를 지정하고 그곳에다가 저장
        File file = new File(absolutePath);
        // 저장할 위치의 디렉토리가 존지하지 않을 경우
        if (!file.exists()) {
            // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
            file.mkdirs();
        }

        InquireDaoEntity fileEntity = new InquireDaoEntity();
        // 파일이 비어 있지 않을 때 작업을 시작해야 오류가 나지 않는다
        if (!multipartFile.isEmpty()) {
            // jpeg, png, gif 파일들만 받아서 처리할 예정
            String contentType = multipartFile.getContentType();
            String originalFileExtension;
            // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("잘못된 파일 입니다.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if (contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                }
                // 다른 파일 명이면 아무 일 하지 않는다
                else {
                    throw new Exception("jpg, png, gif만 업로드 가능합니다.");
                }
            }
            // 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
            String new_file_name = System.nanoTime() + originalFileExtension;

            fileEntity.setInquires_id(inquires_id);
            fileEntity.setPicture_name(multipartFile.getOriginalFilename());
            fileEntity.setPicture_path("/images" + File.separator + new_file_name);

            // 저장된 파일로 변경하여 이를 보여주기 위함
            file = new File(absolutePath + File.separator + new_file_name);
            multipartFile.transferTo(file);
        }
        return fileEntity;
    }

    public void modifyUpload(ProductImageEntity productImageEntity) throws Exception{

        Long productId = productImageEntity.getProduct_id();
        ProductEntity lastProductEntity = productDao.selectProductById(productId);
        List<ProductDaoEntity> lastImageEntity = productDao.selectProductImages(productId);

        String[] imageField = { "list_picture", "list_back_picture", "represent_picture", "desc_picture"};

        for( ProductDaoEntity imageVO : lastImageEntity){
            String type = imageVO.getImg_type();
            String name = imageVO.getImg_name();
            String path = imageVO.getImg_path();
            for(String field : imageField){
                if(type.equals(field)){
                    lastProductEntity.setFieldValue(type, path);
                    lastProductEntity.setFieldValue(type+"_name", name);
                }
            }
        }
        ProductDaoEntity newProductImageEntity = modifyImg(productImageEntity, lastImageEntity);
        System.out.println("last:"+lastProductEntity);
        System.out.println("now:"+productImageEntity);
        System.out.println("new:"+newProductImageEntity);
    }

    public ProductDaoEntity modifyImg(ProductImageEntity productImageVO, List<ProductDaoEntity> lastImageEntity) throws Exception {
        ProductDaoEntity newImageEntity = new ProductDaoEntity();

        String absolutePath = new File("").getAbsolutePath() + "\\" + uploadPath;

        String[] imageField = {"list_picture", "list_back_picture", "represent_picture", "desc_picture"};

        for (String field : imageField) {
            MultipartFile multipartFile = null;
            if (field.equals("represent_picture")) {
                multipartFile = productImageVO.getRepresent_picture();
            } else if (field.equals("list_picture")) {
                multipartFile = productImageVO.getList_picture();
            } else if (field.equals("list_back_picture")) {
                multipartFile = productImageVO.getList_back_picture();
            } else if (field.equals("desc_picture")) {
                List<MultipartFile> descPictures = productImageVO.getDesc_picture();
                if (descPictures != null && !descPictures.isEmpty()) {
                    // desc_picture에 있는 각 파일에 대해 반복하여 처리
                    System.out.println(descPictures);
                    int descIndex = 0;
                    for (MultipartFile descPicture : descPictures) {
                        processDescPicture(descPicture, lastImageEntity, absolutePath, newImageEntity, descPictures.size(), descIndex);
                        descIndex ++;
                    }
                }
            }

            if (multipartFile != null && !multipartFile.isEmpty() && !field.equals("desc_picture")) {
                processImageField(multipartFile, lastImageEntity, absolutePath, newImageEntity);
            }

        }

        return newImageEntity;
    }




    private void processDescPicture(MultipartFile descPicture, List<ProductDaoEntity> lastImageEntity, String absolutePath, ProductDaoEntity newImageEntity, int descListSize, int descIndex) throws Exception {
        boolean addImgState = false;
        if (descPicture != null && !descPicture.isEmpty()) {
            for (ProductDaoEntity imgEntity : lastImageEntity) {
                if (descPicture.getName().equals(imgEntity.getImg_type())) {
                    newImageEntity.setImg_id(imgEntity.getImg_id());
                    newImageEntity.setImg_name(descPicture.getOriginalFilename());
                    newImageEntity.setImg_size(descPicture.getSize());
                    newImageEntity.setImg_type(descPicture.getName());
                    newImageEntity.setProduct_id(imgEntity.getProduct_id());

                    String lastImgPath = absolutePath + imgEntity.getImg_path().replace("/images", "");
                    File previousImageFile = new File(lastImgPath);
                    System.out.println(descPicture.getOriginalFilename());

                    boolean deleted = previousImageFile.delete(); // 기존 이미지 파일 삭제
                    System.out.println("이전 이미지 삭제 여부: " + deleted);
                    System.out.println("이미지Index: " + descIndex);

                    if(deleted){
                        for (ProductDaoEntity oldImgEntity : lastImageEntity) {
                            if (oldImgEntity.getImg_type().equals("desc_picture")) {
                                String oldImgPath = absolutePath + oldImgEntity.getImg_path().replace("/images", "");
                                File oldImageFile = new File(oldImgPath);

                                boolean oldImgDeleted = oldImageFile.delete(); // 기존 이미지 파일 삭제
                                if(oldImgDeleted){
                                    imageDao.deletePictureByImgId(oldImgEntity.getImg_id());

                                }
                                System.out.println("desc이미지가 예전 이미지보다 개수가 작은 경우에 예전 이미지의 삭제 여부 : " + oldImgDeleted);
                            }
                        }
                        break;
                    }
                    addImgState = true;
                }
            }

            String contentType = descPicture.getContentType();
            String originalFileExtension;
            // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("잘못된 파일 입니다.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if (contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                }
                // 다른 파일 명이면 아무 일 하지 않는다
                else {
                    throw new Exception("jpg, png, gif만 업로드 가능합니다.");
                }
            }

            String newFileName = System.nanoTime() + originalFileExtension;
            newImageEntity.setImg_path("/images" + File.separator + newFileName);

            if(addImgState){
                imageDao.saveProductImage(newImageEntity);
                System.out.println("새로운 이미지 등록완료");
            }else{
                productDao.updateProductPicture(newImageEntity);
            }

            File file = new File(absolutePath + File.separator + newFileName);
            descPicture.transferTo(file);
        }
    }

    public void processImageField(MultipartFile multipartFile, List<ProductDaoEntity> lastImageEntity, String absolutePath, ProductDaoEntity newImageEntity) throws Exception {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            for (ProductDaoEntity imgEntity : lastImageEntity) {
                if (multipartFile.getName().equals(imgEntity.getImg_type())) {
                    newImageEntity.setImg_id(imgEntity.getImg_id());
                    newImageEntity.setImg_name(multipartFile.getOriginalFilename());
                    newImageEntity.setImg_size(multipartFile.getSize());
                    newImageEntity.setImg_type(multipartFile.getName());
                    newImageEntity.setProduct_id(imgEntity.getProduct_id());

                    String lastImgPath = absolutePath + imgEntity.getImg_path().replace("/images", "");
                    File previousImageFile = new File(lastImgPath);
                    boolean deleted = previousImageFile.delete(); // 기존 이미지 파일 삭제
                    System.out.println("이전 이미지 삭제 여부: " + deleted);
                }
            }

            String contentType = multipartFile.getContentType();
            String originalFileExtension;
            // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("잘못된 파일 입니다.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if (contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                }
                // 다른 파일 명이면 아무 일 하지 않는다eeee
                else {
                    throw new Exception("jpg, png, gif만 업로드 가능합니다.");
                }
            }

            String new_file_name = System.nanoTime() + originalFileExtension;
            newImageEntity.setImg_path("/images" + File.separator + new_file_name);

            productDao.updateProductPicture(newImageEntity);

            File file = new File(absolutePath + File.separator + new_file_name);
            multipartFile.transferTo(file);
        }
    }

    public void saveImgPath(ProductDaoEntity imgDao) throws Exception{
        List<ProductDaoEntity> ImageListByProductId = imageDao.selectMainPictureByProductId(imgDao.getProduct_id());
        if(ImageListByProductId.size() > 1){
            imageDao.deleteMainPictureByProductId(imgDao.getProduct_id());
        }
        imageDao.saveProductImage(imgDao);
    }

    public void saveInquireImgPath(InquireDaoEntity imgDao) throws Exception{
        imageDao.saveInquireImage(imgDao);
    }

    public void deleteImg(Long product_id) throws Exception{
        List<ProductDaoEntity> ImageListByProductId = imageDao.selectPictureByProductId(product_id);

        String absolutePath = new File("").getAbsolutePath() + "\\" + uploadPath;

        imageDao.deletePictureByProductId(product_id);

        for( ProductDaoEntity imageEntity : ImageListByProductId){
            String path = imageEntity.getImg_path();
            String lastImgPath = absolutePath + path.replace("/images", "");
            File file = new File(lastImgPath);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File delete successfully");
                } else {
                    System.out.println("Failed to delete file");
                }
            } else {
                System.out.println("File not found");
            }
        }
    }
    public void deleteMainImg(Long product_id) throws Exception{
        List<ProductDaoEntity> ImageListByProductId = imageDao.selectPictureByProductId(product_id);

        String absolutePath = new File("").getAbsolutePath() + "\\" + uploadPath;

        imageDao.deleteMainPictureByProductId(product_id);
        productDao.setMainProduct(product_id);

        for( ProductDaoEntity imageEntity : ImageListByProductId){
            String imageEntityType = imageEntity.getImg_type();
            if(imageEntityType.equals("main_picture")){
                String path = imageEntity.getImg_path();
                String lastImgPath = absolutePath + path.replace("/images", "");
                File file = new File(lastImgPath);
                if (file.exists()) {
                    if (file.delete()) {
                        System.out.println("File delete successfully");
                    } else {
                        System.out.println("Failed to delete file");
                    }
                } else {
                    System.out.println("File not found");
                }
            }
        }
    }


    public InquireDaoEntity deleteBeforeInquireImg( String existedFileName, Long inquires_id) throws Exception {
        InquireDaoEntity inquireDaoEntity = imageDao.selectInquireImageByPictureName(existedFileName, inquires_id);
        String absolutePath = new File("").getAbsolutePath() + "\\" + uploadPath;

        String path = inquireDaoEntity.getPicture_path();
        String lastImgPath = absolutePath + path.replace("/images", "");
        File file = new File(lastImgPath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File delete successfully");
            } else {
                System.out.println("Failed to delete file");
            }
        } else {
            System.out.println("File not found");
        }
        return inquireDaoEntity;
    }

    public void deleteInquireImg(InquireDaoEntity inquireDaoEntiy) throws Exception{
        Long inquirePictureId = inquireDaoEntiy.getInquire_picture_id();
        imageDao.deleteInquirePicturesByPictureId(inquirePictureId);
    }

    public InputStreamResource loadFileAsResource(String fileName) throws IOException {
        // 리소스 핸들러로부터 이미지의 상대 경로 가져오기
        String relativePath = "static/images/" + fileName; // 예를 들어 images 폴더 안에 이미지가 있다고 가정

        // 리소스 핸들러로부터 이미지의 절대 경로 가져오기
        Resource resource = new ClassPathResource(relativePath);

        if (resource.exists() && resource.isReadable()) {
            return new InputStreamResource(resource.getInputStream());
        } else {
            throw new IOException("File not found " + fileName);
        }
    }
}
