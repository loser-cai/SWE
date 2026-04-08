package com.caicai.swe.mapper;

import com.caicai.swe.entity.Product;
import com.caicai.swe.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品Mapper
 */
@Mapper
public interface ProductMapper {

    /**
     * 根据ID查询商品
     */
    Product selectById(@Param("id") Long id);

    /**
     * 查询商品VO
     */
    ProductVO selectVOById(@Param("id") Long id);

    /**
     * 分页查询商品列表
     */
    List<ProductVO> selectList(@Param("keyword") String keyword,
                                @Param("categoryId") Long categoryId,
                                @Param("status") Integer status,
                                @Param("userId") Long userId,
                                @Param("offset") Integer offset,
                                @Param("limit") Integer limit);

    /**
     * 查询商品总数
     */
    Long selectCount(@Param("keyword") String keyword,
                     @Param("categoryId") Long categoryId,
                     @Param("status") Integer status,
                     @Param("userId") Long userId);

    /**
     * 插入商品
     */
    int insert(Product product);

    /**
     * 更新商品
     */
    int update(Product product);

    /**
     * 根据ID删除商品
     */
    int deleteById(@Param("id") Long id);

    /**
     * 增加浏览次数
     */
    int incrementViewCount(@Param("id") Long id);

    /**
     * 增加收藏次数
     */
    int incrementFavoriteCount(@Param("id") Long id);

    /**
     * 减少收藏次数
     */
    int decrementFavoriteCount(@Param("id") Long id);
}