package com.example.keyboard.repository;

import com.example.keyboard.repository.product.ProductDetailEntity;
import com.example.keyboard.repository.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductDao {

    public void insertListProduct(ProductEntity productVO) throws Exception;

    public List<ProductEntity> selectListProduct() throws Exception;

    public void insertProductDetail(ProductDetailEntity productDetailVO) throws Exception;
    public void insertDetailCategory(ProductDetailEntity productDetailVO) throws Exception;

    public List<ProductDetailEntity> selectProductDetailList(@Param("productId") Long productId) throws Exception;
    public List<ProductDetailEntity> selectProductCategory(@Param("productDetailId") Long productDetailId) throws Exception;

    public void updateProductDefault(@Param("product_id") Long product_id, @Param("product_detail_id")Integer product_detail_id) throws Exception;

    public void insertMainPic(@Param("product_id") Long product_id, @Param("main_pic_path") String main_pic_path) throws Exception;
    public void updateMainPicState(@Param("product_id") Long product_id, @Param("main_pic_state") Integer main_pic_state) throws Exception;
}

