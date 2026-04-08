package com.caicai.swe.service;

import com.caicai.swe.common.PageResult;
import com.caicai.swe.dto.ProductPublishDTO;
import com.caicai.swe.dto.ProductQueryDTO;
import com.caicai.swe.entity.Product;
import com.caicai.swe.vo.ProductVO;

/**
 * 商品服务接口
 */
public interface ProductService {

    /**
     * 发布商品
     */
    Long publish(ProductPublishDTO dto, Long userId);

    /**
     * 获取商品详情
     */
    ProductVO getProductById(Long id);

    /**
     * 获取商品列表（分页）
     */
    PageResult<ProductVO> getProductList(ProductQueryDTO dto);

    /**
     * 更新商品
     */
    Boolean updateProduct(Product product);

    /**
     * 删除商品
     */
    Boolean deleteProduct(Long id);

    /**
     * 增加浏览次数
     */
    void incrementViewCount(Long id);

    /**
     * 更新收藏次数
     */
    void updateFavoriteCount(Long id, boolean increment);
}