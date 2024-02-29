package com.example.keyboard.repository;

import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductDao {

    public void insertProduct(ProductEntity productVO) throws Exception;

    public List<ProductEntity> selectListProduct() throws Exception;
    public List<ProductDetailEntity> selectAllProductDetailList() throws Exception;

    public void insertProductDetail(ProductDetailEntity productDetailVO) throws Exception;
    public void insertProductCategory(@Param("product_id") Long product_id, @Param("category_name") String category_name) throws Exception;

    public Long selectProductCategoryId(@Param("product_id") Long product_id, @Param("category_name") String category_name) throws Exception;

    public List<ProductDetailEntity> selectProductCategory(@Param("product_detail_id") Long product_detail_id) throws Exception;
    public List<ProductDetailEntity> selectProductDetailList(@Param("product_id") Long product_id) throws Exception;

    public void updateProductDefault(@Param("product_id") Long product_id, @Param("product_detail_id")Integer product_detail_id) throws Exception;

    public void insertMainPic(@Param("product_id") Long product_id, @Param("main_pic_path") String main_pic_path) throws Exception;
    public void updateMainPicState(@Param("product_id") Long product_id, @Param("main_pic_state") Integer main_pic_state) throws Exception;

    public List<ProductEntity> selectMainProduct() throws Exception;

    public void UpdateProduct(ProductEntity productVO) throws Exception;

    public void updateProductDetail(ProductDetailEntity productDetailVO) throws Exception;
    public void updateDetailCategory(ProductDetailEntity productDetailVO) throws Exception;
}
