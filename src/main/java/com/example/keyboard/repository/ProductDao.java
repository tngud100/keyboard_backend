package com.example.keyboard.repository;

import com.example.keyboard.entity.Image.ImageEntity;
import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ProductDao {

    public void insertProduct(@Param("name") String name, @Param("type") String type) throws Exception;
    public void insertProductDetail(ProductDetailEntity productDetailVO) throws Exception;
    public void insertProductCategory(Map<String, Object> categoryInfo) throws Exception;
    public void insertMainPic(@Param("product_id") Long product_id, @Param("main_pic_path") String main_pic_path, @Param("main_pic_state") Integer main_pic_state) throws Exception;

    public List<ProductEntity> selectAllProductList() throws Exception;
    public Long selectProductIdByName(@Param("name") String name) throws Exception;
    public ProductEntity selectProductById(@Param("product_id") Long product_id) throws Exception;
    public List<ImageEntity> selectProductImages(@Param("product_id") Long product_id) throws Exception;
    public List<ProductDetailEntity> selectAllProductDetailList() throws Exception;
    public ProductDetailEntity selectProductDetail(@Param("product_detail_id") Long product_detail_id) throws Exception;
    public List<ProductDetailEntity> selectSameCategoryDetailList(@Param("product_id") Long product_id, @Param("product_category_id") Long product_category_id) throws Exception;
    public int selectDetailCategoryState(@Param("product_id") Long product_id, @Param("product_category_id") Long product_category_id) throws Exception;
    public ProductDetailEntity selectProductCategory(@Param("product_category_id") Long product_category_id) throws Exception;
    public List<ProductDetailEntity> selectCategoryByProductId(@Param("product_id") Long product_id) throws Exception;
    public List<ProductDetailEntity> selectProductDetailList(@Param("product_id") Long product_id) throws Exception;
    public List<ProductDetailEntity> selectProductDetailByCategory(@Param("product_category_id") Long product_category_id) throws Exception;
    public List<ProductEntity> selectMainProduct() throws Exception;

    public String selectCategoryName(@Param("product_category_id") Long product_category_id) throws Exception;
    public String selectProductName(@Param("product_id") Long product_id) throws Exception;
    public String selectProductDetailName(@Param("product_detail_id") Long product_detail_id) throws Exception;
    public int selectProductDetailAmount(@Param("product_detail_id") Long product_detail_id) throws Exception;
    public ProductDetailEntity selectDefaultedDetailByCategoryId(@Param("product_category_id") Long product_category_id) throws Exception;


    public int isProductNameExists(@Param("name") String name) throws Exception;
    public int isDetailNameExists(@Param("product_id") Long product_id, @Param("name") String name) throws Exception;
    public int isCategoryNameExists(@Param("product_id") Long product_id, @Param("category_name") String category_name) throws Exception;


    public void updateProductDefault(@Param("product_detail_id")Long product_detail_id) throws Exception;
    public void updateCategoryDefault(@Param("product_category_id")Long product_category_id) throws Exception;
    public void updateProductAmount(@Param("product_id") Long product_id, @Param("amount") int amount) throws Exception;
    public void updateProduct(ProductEntity modifiedEntity) throws Exception;
    public void updateProductPicture(ImageEntity modifiedImageEntity) throws Exception;
    public void updateProductDetail(ProductDetailEntity productDetailVO) throws Exception;
    public void updateProductCategory(Map<String, Object> categoryInfo) throws Exception;
    public void updateMainPicState(@Param("product_id") Long product_id, @Param("main_pic_state") Integer main_pic_state) throws Exception;


    public void deleteProduct(@Param("product_id") Long product_id) throws Exception;
    public void deleteCategoryByProductId(@Param("product_id") Long product_id) throws Exception;
    public void deleteProductDetailByProductId(@Param("product_id") Long product_id) throws Exception;

    public void deleteCategory(@Param("product_category_id") Long product_category_id) throws Exception;
    public void deleteProductDetail(@Param("product_detail_id") Long product_detail_id) throws Exception;

}
