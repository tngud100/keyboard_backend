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
    // 상품 가져오기
    public List<ProductEntity> selectProductList() throws Exception{
        return productDao.selectListProduct();
    }
    // 상품 수정하기
    public void updateProduct(ProductEntity vo) throws Exception{
        productDao.UpdateProduct(vo);
    }


    // 상품 카테고리 등록
    public void enrollProductCategory(Long product_id, String category_name) throws Exception {
        productDao.insertProductCategory(product_id, category_name);
    }


    // 상세 상품 등록
    public void enrollProductDetail(ProductDetailEntity vo) throws  Exception{
        Long product_Id = vo.getProduct_id();
        String Category_name = vo.getCategory_name();
        Long product_category_id = productDao.selectProductCategoryId(product_Id, Category_name);

        vo.setCategory_id(product_category_id);

        productDao.insertProductDetail(vo);
    }
    // 상품의 상세 상품 가져오기
    public List<ProductDetailEntity> selectProductDetailList(Long productId) throws Exception{
        List<ProductDetailEntity> productDetail = productDao.selectProductDetailList(productId);
        List<ProductDetailEntity> productDetailEntity = new ArrayList<>();

        for( ProductDetailEntity productVO : productDetail){
            List<ProductDetailEntity> productCategory = productDao.selectProductCategory(productVO.getProduct_detail_id());

            ProductDetailEntity EntityVO = new ProductDetailEntity();

            EntityVO.setProduct_id(productVO.getProduct_id());
            EntityVO.setProduct_detail_id(productVO.getProduct_detail_id());
            EntityVO.setName(productVO.getName());
            EntityVO.setAmount(productVO.getAmount());
            EntityVO.setStock(productVO.getStock());
            EntityVO.setDefault_state(productVO.getDefault_state());

            for( ProductDetailEntity categoryVO : productCategory ) {
                EntityVO.setCategory_name(categoryVO.getCategory_name());
                EntityVO.setCategory_id(categoryVO.getCategory_id());

                productDetailEntity.add(EntityVO);
            }
        }

        return productDetailEntity;


    }
    // 모든 상세 상품 가져오기
    public List<ProductDetailEntity> selectAllProductDetailList() throws Exception{
        List<ProductDetailEntity> productDetailList = productDao.selectAllProductDetailList();
        List<ProductDetailEntity> allProductDetailList = new ArrayList<>();

        // 모든 ProductDetail가져와서 allProductDetailList에 집어넣기
        for( ProductDetailEntity productDetail : productDetailList ){
            ProductDetailEntity productDetailVO = new ProductDetailEntity();
            Long category_id = productDetail.getCategory_id();
            List<ProductDetailEntity> productDetailCategoryList = productDao.selectProductCategory(category_id);

            // 카테고리가 있을시에 VO에 넣어 새로운 객체 만들기
            if( productDetailCategoryList != null){
                for( ProductDetailEntity detailCategory : productDetailCategoryList){
                    productDetail.setCategory_name(detailCategory.getCategory_name());
                    productDetail.setCategory_state(detailCategory.getCategory_state());
                    productDetail.setCategory_id(detailCategory.getCategory_id());

                    allProductDetailList.add(productDetail);
                }
            }else{
                allProductDetailList.add(productDetail);
            }

        }
        return productDetailList;
    }
    // 상세 상품 수정하기
    public void updateProductDetail(ProductDetailEntity vo) throws Exception{
        productDao.updateProductDetail(vo);

        Long product_detail_id = vo.getProduct_detail_id();
        String category_name = vo.getCategory_name();
        int category_state = vo.getCategory_state();

        if (category_name != null && !category_name.isEmpty() || category_state != 0) {
            ProductDetailEntity categoryVO = new ProductDetailEntity();
            categoryVO.setProduct_detail_id(product_detail_id);
            categoryVO.setCategory_name(category_name);
            categoryVO.setCategory_state(category_state);

            List<ProductDetailEntity> existingCategory = productDao.selectProductCategory(product_detail_id);

            if (existingCategory != null) {
                // 카테고리 정보가 이미 존재하면 업데이트
                productDao.updateDetailCategory(categoryVO);
            } else {
                // 카테고리 정보가 존재하지 않으면 삽입
//                productDao.insertDetailCategory(categoryVO);
            }
        }
    }



    // 상세 상품 기본값 설정
    public void setProductDetailDefault(Long product_id, Integer product_detail_id) throws Exception{
        productDao.updateProductDefault(product_id, product_detail_id);
    }

    // 상품 메인으로 설정
    public void setMainProduct(ProductEntity vo) throws Exception{
        String main_pic_path = vo.getMain_picture();
        Integer main_pic_state = vo.getMain_pic_state();
        Long product_id = vo.getProduct_id();

        productDao.insertMainPic(product_id, main_pic_path);
        productDao.updateMainPicState(product_id, main_pic_state);
    }

    // main_pic_state가 1인 상품 가져오기
    public List<ProductEntity> selectMainProductList() throws Exception{
        return productDao.selectMainProduct();
    }
}
