package com.example.keyboard.repository;

import com.example.keyboard.entity.Image.ImageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ImageDao {
    public void saveProductImage(ImageEntity imageVO) throws Exception;
    public List<ImageEntity> selectPictureByProductId(@Param("product_id") Long product_id) throws Exception;
    public void deletePictureByImgId(@Param("img_id") Long img_id) throws Exception;
    public void deletePictureByProductId(@Param("product_id") Long product_id) throws Exception;

}
