package com.example.keyboard.service;

import com.example.keyboard.entity.Image.ImageEntity;
import com.example.keyboard.repository.ImageDao;
import com.example.keyboard.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ImgUploadService {

    @Value("${upload.path}") // application.properties에 설정된 이미지 업로드 경로를 가져옵니다.
    private String uploadPath;

    private final ImageDao imageDao;
    private ImageEntity fileEntity;
    private final ProductDao productDao;

    public ImageEntity uploadImg(MultipartFile multipartFile, Long product_id) throws Exception {

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if (multipartFile.isEmpty()) {
            throw new Exception("파일이 비어있습니다.");
        }

        // 파일 이름을 업로드 한 날짜로 바꾸어서 저장할 것이다
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        // 프로젝트 폴더에 저장하기 위해 절대경로를 설정 (Window 의 Tomcat 은 Temp 파일을 이용한다)
        String absolutePath = new File("").getAbsolutePath() + "\\"+uploadPath;

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
            // 생성 후 리스트에 추가
            fileEntity = new ImageEntity();
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

    public void saveImgPath(ImageEntity imgDao) throws Exception{
        imageDao.saveProductImage(imgDao);
    }
    public void insertProduct(String name, String type) throws Exception{
        productDao.insertProduct(name, type);
    }
}
