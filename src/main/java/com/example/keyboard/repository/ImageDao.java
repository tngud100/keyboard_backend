package com.example.keyboard.repository;

import com.example.keyboard.entity.Image.product.ProductDaoEntity;
import com.example.keyboard.entity.Image.inquire.InquireDaoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ImageDao {
    public void saveProductImage(ProductDaoEntity imageVO) throws Exception;
    public List<ProductDaoEntity> selectPictureByProductId(@Param("product_id") Long product_id) throws Exception;
    public List<ProductDaoEntity> selectMainPictureByProductId(@Param("product_id") Long product_id) throws Exception;
    public void deletePictureByImgId(@Param("img_id") Long img_id) throws Exception;
    public void deletePictureByProductId(@Param("product_id") Long product_id) throws Exception;
    public void deleteMainPictureByProductId(@Param("product_id") Long product_id) throws Exception;

    public void saveInquireImage(InquireDaoEntity imageVO) throws Exception;
    public List<InquireDaoEntity> selectInquireImage(@Param("inquires_id") Long inquires_id) throws Exception;
    public InquireDaoEntity selectInquireImageByPictureName(@Param("picture_name") String picture_name, @Param("inquires_id") Long inquires_id) throws Exception;

    public void deleteInquirePicturesById(@Param("inquires_id") Long inquires_id) throws Exception;
    public void deleteInquirePicturesByPictureId(@Param("inquire_picture_id") Long inquire_picture_id) throws Exception;
}
