package com.example.keyboard.service;

import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import com.example.keyboard.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final ProductDao productDao;

    // 상품 등록
    public void productEnroll(ProductEntity vo) throws Exception {
        productDao.insertProduct(vo);
    }
    // 상품 카테고리 등록
    public void enrollProductCategory(Long product_id, String category_name, int category_state) throws Exception {
        Map<String, Object > categoryInfo = new HashMap<>();
        categoryInfo.put("product_id",product_id);
        categoryInfo.put("category_name", category_name);
        categoryInfo.put("category_state", category_state);
        productDao.insertProductCategory(categoryInfo);
    }
    // 상세 상품 등록
    public void enrollProductDetail(ProductDetailEntity vo) throws  Exception{
        productDao.insertProductDetail(vo);
    }



    // 상품 가져오기
    public List<ProductEntity> selectProductList() throws Exception{
        return productDao.selectListProduct();
    }
    // 상품의 상세 상품 가져오기
    public List<ProductDetailEntity> selectProductDetailList(Long productId) throws Exception{
        List<ProductDetailEntity> productDetail = productDao.selectProductDetailList(productId);
        List<ProductDetailEntity> productDetailEntity = new ArrayList<>();

        for( ProductDetailEntity productVO : productDetail){
            ProductDetailEntity productCategory = productDao.selectProductCategory(productVO.getProduct_category_id());

            ProductDetailEntity EntityVO = new ProductDetailEntity();

            EntityVO.setProduct_id(productVO.getProduct_id());
            EntityVO.setProduct_detail_id(productVO.getProduct_detail_id());
            EntityVO.setName(productVO.getName());
            EntityVO.setAmount(productVO.getAmount());
            EntityVO.setStock(productVO.getStock());
            EntityVO.setDefault_state(productVO.getDefault_state());

            EntityVO.setCategory_name(productCategory.getCategory_name());
            EntityVO.setProduct_category_id(productCategory.getProduct_category_id());

            productDetailEntity.add(EntityVO);
        }

        return productDetailEntity;


    }
    // 모든 상세 상품 가져오기
    public List<ProductDetailEntity> selectAllProductDetailList() throws Exception{
        List<ProductDetailEntity> productDetailList = productDao.selectAllProductDetailList();
        List<ProductDetailEntity> allProductDetailList = new ArrayList<>();

        // 모든 ProductDetail가져와서 allProductDetailList에 집어넣기
        for( ProductDetailEntity productDetail : productDetailList ){
            Long category_id = productDetail.getProduct_category_id();
            ProductDetailEntity productDetailVO = productDao.selectProductCategory(category_id);

            productDetail.setCategory_name(productDetailVO.getCategory_name());
            productDetail.setCategory_state(productDetailVO.getCategory_state());
            productDetail.setProduct_category_id(productDetailVO.getProduct_category_id());

            allProductDetailList.add(productDetail);
        }
        return productDetailList;
    }
    // main_pic_state가 1인 상품 가져오기
    public List<ProductEntity> selectMainProductList() throws Exception{
        return productDao.selectMainProduct();
    }



    // 상품 수정하기
    public void updateProduct(ProductEntity vo) throws Exception{
        productDao.UpdateProduct(vo);
    }
    // 상품 카테고리 수정
    public void updateProductCategory(Long product_id, Long product_category_id,
                                      String category_name, int category_state) throws Exception{
        Map<String, Object > categoryInfo = new HashMap<>();
        categoryInfo.put("product_id",product_id);
        categoryInfo.put("product_category_id", product_category_id);
        categoryInfo.put("category_name", category_name);
        categoryInfo.put("category_state", category_state);
        productDao.updateProductCategory(categoryInfo);
    }
    // 상세 상품 수정하기
    public void updateProductDetail(ProductDetailEntity vo) throws Exception{
        productDao.updateProductDetail(vo);
    }


    // 상세 상품 기본값 설정
    public void setProductDetailDefault(Long product_id, Long product_detail_id) throws Exception{
        productDao.updateProductDefault(product_id, product_detail_id);
    }
    // 카테고리 기본값 설정
    public void setProductCategoryDefault(Long product_id, Long product_category_id) throws Exception{
        productDao.updateCategoryDefault(product_id, product_category_id);
    }
    // 상품 메인으로 설정
    public void setMainProduct(ProductEntity vo) throws Exception{
        String main_pic_path = vo.getMain_picture();
        Integer main_pic_state = vo.getMain_pic_state();
        Long product_id = vo.getProduct_id();

        productDao.insertMainPic(product_id, main_pic_path, main_pic_state);
    }


}
