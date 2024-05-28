package com.example.keyboard.service;

import com.example.keyboard.controller.ImageController;
import com.example.keyboard.entity.Image.product.ProductDaoEntity;
import com.example.keyboard.entity.Image.product.ProductImageEntity;
import com.example.keyboard.entity.product.ProductDetailEntity;
import com.example.keyboard.entity.product.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    public final com.example.keyboard.repository.ProductDao productDao;
    public final ImageController imgController;

    // 상품 등록
    public Long productEnroll(String name, String type) throws Exception {
        productDao.insertProduct(name, type);
        return productDao.selectProductIdByName(name);
    }
    // 상품 카테고리 등록
    public void enrollProductCategory(Long product_id, String category_name, int category_state) throws Exception {
        // 해당 상품의 카테고리를 검색하고 최초 등록일시에 자동으로 category_state가 1로 지정
        List<ProductDetailEntity> detailEntityVO = productDao.selectCategoryByProductId(product_id);

        if (detailEntityVO == null || detailEntityVO.isEmpty()) {
            category_state = 1;
        }

        Map<String, Object > categoryInfo = new HashMap<>();
        categoryInfo.put("product_id",product_id);
        categoryInfo.put("category_name", category_name);
        categoryInfo.put("category_state", category_state);
        productDao.insertProductCategory(categoryInfo);
    }
    // 상세 상품 등록
    public void enrollProductDetail(ProductDetailEntity vo) throws  Exception{
        // category_state의 값이 1이며 최초 등록이면 default를 1로 지정 하며 상세 상품의 가격이 상품 가격에 더해짐
        int category_state = productDao.selectDetailCategoryState(vo.getProduct_id(), vo.getProduct_category_id());
        List<ProductDetailEntity> detailList = productDao.selectSameCategoryDetailList(vo.getProduct_id(), vo.getProduct_category_id());

        if(detailList.isEmpty() && category_state == 1){
            vo.setDefault_state(1);
            productDao.updateProductAmount(vo.getProduct_id(), vo.getAmount());
        }

        productDao.insertProductDetail(vo);
    }


    // 상품 가져오기
    public List<ProductEntity> selectProductList() throws Exception{
        return productDao.selectAllProductList();
    }
    public ProductEntity selectProduct(Long product_id) throws Exception{
        ProductEntity product = productDao.selectProductById(product_id);
        List<ProductDaoEntity> ImageList = selectProductImgList(product_id);

        for(ProductDaoEntity productImg : ImageList){
            String Img_type = productImg.getImg_type();
            String Img_path = productImg.getImg_path();
            String Img_name = productImg.getImg_name();

            if(Img_type.equals("represent_picture")){
                product.setRepresent_picture(Img_path);
                product.setRepresent_picture_name(Img_name);
            }else if(Img_type.equals("list_picture")){
                product.setList_picture(Img_path);
                product.setList_picture_name(Img_name);
            }else if(Img_type.equals("list_back_picture")){
                product.setList_back_picture(Img_path);
                product.setList_back_picture_name(Img_name);
            }else if(Img_type.equals("desc_picture")){
                product.getDesc_picture().add(Img_path);
                product.getDesc_picture_name().add(Img_name);
            }
        }
        return product;
    }
    // 상품의 이미지 가져오기
    public List<ProductDaoEntity> selectProductImgList(Long product_id) throws Exception{
        return productDao.selectProductImages(product_id);
    }
    // 상품의 카테고리 가져오기
    public List<ProductDetailEntity> selectProductCategoryList(Long product_id) throws Exception{
        return productDao.selectCategoryByProductId(product_id);
    }
    // 상품의 카테고리별 상세 상품 가져오기
    public List<ProductDetailEntity> selectProductCategoryDetailList(Long product_id, Long product_category_id) throws Exception{
        return productDao.selectSameCategoryDetailList(product_id, product_category_id);
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
        List<ProductEntity> ProductEntities = productDao.selectMainProduct();
        for(ProductEntity VO :  ProductEntities){
            List<ProductDaoEntity> ImgVO = productDao.selectProductImages(VO.getProduct_id());
            for(ProductDaoEntity img : ImgVO){
                if(img.getImg_type().equals("main_picture")){
                    String name = img.getImg_name();
                    String path = img.getImg_path();
                    VO.setMain_picture(path);
                    VO.setMain_picture_name(name);
                }
            }
        }


        return ProductEntities;
    }
    // 이름 존재 여부 확인하기
    public boolean isProductNameExists(String name) throws Exception{
        int nameExist = productDao.isProductNameExists(name);
        return nameExist > 0;
    }
    public boolean isDetailNameExists(Long product_id, String name) throws Exception{
        int nameExist = productDao.isDetailNameExists(product_id, name);
        return nameExist > 0;
    }
    public boolean isCategoryNameExists(Long product_id, String name) throws Exception{
        int nameExist = productDao.isCategoryNameExists(product_id, name);
        return nameExist > 0;
    }
    // 이름 가져오기
    public String selectProductName(Long product_id) throws Exception{
        return productDao.selectProductName(product_id);
    }
    public String selectCategoryName(Long product_category_id) throws Exception{
        return productDao.selectCategoryName(product_category_id);
    }
    public String selectProductDetailName(Long product_detail_id) throws Exception{
        return productDao.selectProductDetailName(product_detail_id);
    }



    // 상품 수정하기
    public void updateProduct(ProductImageEntity vo) throws Exception {
        Long productId = vo.getProduct_id();

        ProductEntity modifiedEntity = new ProductEntity();

        ProductEntity lastProductEntity = productDao.selectProductById(productId);

        if (vo.getName().equals(lastProductEntity.getName())) {
            modifiedEntity.setName(lastProductEntity.getName());
        } else {
            modifiedEntity.setName(vo.getName());
        }

        if (vo.getProduct_type().equals(lastProductEntity.getType())) {
            modifiedEntity.setType(lastProductEntity.getType());
        } else {
            modifiedEntity.setType(vo.getProduct_type());
        }
        modifiedEntity.setProduct_id(productId);
        System.out.println(modifiedEntity);
        productDao.updateProduct(modifiedEntity);
        imgController.modifyUploadImg(vo);
    }
    // 상품 카테고리 수정
    public void updateProductCategory(Long product_id, Long product_category_id,
                                      String category_name) throws Exception{
        Map<String, Object > categoryInfo = new HashMap<>();
        categoryInfo.put("product_id",product_id);
        categoryInfo.put("product_category_id", product_category_id);
        categoryInfo.put("category_name", category_name);
        productDao.updateProductCategory(categoryInfo);
    }
    // 상세 상품 수정하기
    public void updateProductDetail(ProductDetailEntity vo) throws Exception{
        ProductDetailEntity productEntity = productDao.selectProductDetail(vo.getProduct_detail_id());
        int defaultState = productEntity.getDefault_state();

        int amount = vo.getAmount();

        int origin_amount = productDao.selectProductDetailAmount(vo.getProduct_detail_id());

        if(amount != origin_amount && defaultState == 1){
            productDao.updateProductAmount(productEntity.getProduct_id(), -origin_amount);
            productDao.updateProductAmount(productEntity.getProduct_id(), amount);
        }
        productDao.updateProductDetail(vo);
    }


    // 상세 상품 기본값 설정
    public void setProductDetailDefault(Long product_id, Long product_detail_id) throws Exception{
        ProductDetailEntity detailVO = productDao.selectProductDetail(product_detail_id);
        Long category_id = detailVO.getProduct_category_id(); // 카테고리 id

        ProductDetailEntity categoryVO = productDao.selectProductCategory(category_id);
        int category_state = categoryVO.getCategory_state(); // 카테고리 state

        List<ProductDetailEntity> detailList = productDao.selectSameCategoryDetailList(product_id, category_id);

        boolean hasDefaultDetail = false;
        for (ProductDetailEntity vo : detailList) {
            // 기존에 default가 1인 상세 상품이 있는지 확인
            if (vo.getDefault_state() == 1 && vo.getProduct_detail_id().equals(detailVO.getProduct_detail_id())){
                hasDefaultDetail = true;
                break;
            }
        }

        // 카테고리 state가 1이고, 자기 자신의 default를 1에서 0으로 만드려고 할때 예외 발생
        if (category_state == 1 && hasDefaultDetail) {
            throw new Exception("카테고리가 기본값이 1일때 상세상품 중 하나는 default가 1이 존재 해야합니다");
        }

        // 기존에 default가 1인 상세 상품이 있을 경우, 기존 default를 0으로 변경
        // 선택한 상세 상품의 default를 1로 변경
        // 상품에 상세상품의 가격 빼기
        for (ProductDetailEntity vo : detailList) {
            if (vo.getDefault_state() == 1) {
                productDao.updateProductDefault(vo.getProduct_detail_id());
                if(category_state == 1){
                    productDao.updateProductAmount(vo.getProduct_id(), -vo.getAmount());
                }
                break;
            }
        }

        if(!hasDefaultDetail){
            productDao.updateProductDefault(detailVO.getProduct_detail_id());
            if(category_state == 1){
                productDao.updateProductAmount(detailVO.getProduct_id(), detailVO.getAmount());
            }
        }

    }

    // 상품 카테고리 기본값 설정
    public void setProductCategoryDefault(Long product_id, Long product_category_id) throws Exception{
        ProductDetailEntity categoryVO = productDao.selectProductCategory(product_category_id);
        int category_state = categoryVO.getCategory_state();

        // 카테고리를 1로 만들시에 상세 상품의 기본값이 설정되어 있어야 바뀜,즉 상세 상품의 기본값이 모두 0일때 카테고리 state가 1로 안바뀜
        if(category_state == 0){
            List<ProductDetailEntity> detailList = productDao.selectSameCategoryDetailList(product_id, product_category_id);
            boolean isDefaultState = true;
            for (ProductDetailEntity detailVO : detailList) {
                if(detailVO.getDefault_state() == 1){
                    productDao.updateProductAmount(detailVO.getProduct_id(), detailVO.getAmount());
                    isDefaultState = false;
                    break;
                }
            }
            if (isDefaultState) {
                throw new Exception("상세 상품의 default값을 1로 바꿔주세요");
            }
        }
        //category_state가 1에서 0으로 바뀔시에 해당 상품의 카테고리를 모두 검색하여 다른 카테고리의 state가 1이 없을 경우 error 반환
        if(category_state == 1){
            List<ProductDetailEntity> categoryList = productDao.selectCategoryByProductId(product_id);

            // 다른 카테고리의 상태가 모두 0인지 확인
            boolean isCheckCategoryState = true;
            for (ProductDetailEntity vo : categoryList) {
                int state = vo.getCategory_state();
                if (!vo.getProduct_category_id().equals(product_category_id) && state == 1) {
                    ProductDetailEntity detailVO = productDao.selectDefaultedDetailByCategoryId(product_category_id);
                    int amount = detailVO.getAmount();
                    productDao.updateProductAmount(detailVO.getProduct_id(), - amount);

                    isCheckCategoryState = false;
                    break;
                }
            }

            // 다른 카테고리의 상태가 모두 0이 아닌 경우 오류 반환
            if (isCheckCategoryState) {
                throw new Exception("상품의 카테고리는 최소한 하나를 기본값으로 설정되어야 합니다");
            }
        }
        productDao.updateCategoryDefault(product_category_id);
    }
    public void setMainProduct(Long product_id) throws Exception{
        productDao.setMainProduct(product_id);
    }

    // 모두 삭제
    public void deleteProduct(Long product_id) throws Exception{
        productDao.deleteProduct(product_id);
        productDao.deleteCategoryByProductId(product_id);
        productDao.deleteProductDetailByProductId(product_id);
        imgController.deleteImg(product_id);
    }

    // 카테고리를 삭제 할 시에 그에 해당하는 상세 상품의 디폴트 값이 1인 가격을 상품 가격에서 빼준다
    // 카테고리 삭제 시에 해당 상품의 카테고리에 기본값을 가진 다른 카테고리가 있어야지 삭제가 가능하다
    public void deleteProductCategory(Long product_category_id, Long product_id) throws Exception{
        List<ProductDetailEntity> detailList = productDao.selectProductDetailByCategory(product_category_id);
        List<ProductDetailEntity> categoryList = productDao.selectCategoryByProductId(product_id);

        boolean flag = false;
        for(ProductDetailEntity categoryVO : categoryList){
            if(categoryVO.getCategory_state() == 1 && !categoryVO.getProduct_category_id().equals(product_category_id)){
                flag = true;
            }
        }

        if(!flag){
            throw new Exception("다른 카테고리에 기본값을 설정해 주세요. 상품의 기본값이 설정된 유일한 카테고리는 삭제 할 수 없습니다.");
        }

        for(ProductDetailEntity vo : detailList){
            productDao.deleteProductDetail(vo.getProduct_detail_id());
            if(vo.getDefault_state() == 1){
                productDao.updateProductAmount(vo.getProduct_id(), - vo.getAmount());
            }
        }
        productDao.deleteCategory(product_category_id);

    }

    // 카테고리가 1이고 기본 값이 1일때 상세 상품은 삭제 할 수 없다
    public void deleteProductDetail(Long product_detail_id) throws Exception{
        ProductDetailEntity detailVO = productDao.selectProductDetail(product_detail_id);

        Long category = detailVO.getProduct_category_id();
        ProductDetailEntity categoryVO = productDao.selectProductCategory(category);

        if(categoryVO.getCategory_state() == 1 && detailVO.getDefault_state() == 1){
            throw new Exception("카테고리의 state가 1이며, 상세상품이 기본 값으로 설정되어 있으면 삭제 할 수 없습니다. 상세 상품 혹은 카테고리의 기본값을 바꿔주세요");
        }

        productDao.deleteProductDetail(product_detail_id);
    }
}
