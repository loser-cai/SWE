package com.caicai.swe.mapper;

import com.caicai.swe.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏Mapper
 */
@Mapper
public interface FavoriteMapper {

    /**
     * 根据用户ID和商品ID查询收藏
     */
    Favorite selectByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 查询用户的收藏列表
     */
    List<Long> selectProductIdsByUserId(@Param("userId") Long userId);

    /**
     * 插入收藏
     */
    int insert(Favorite favorite);

    /**
     * 根据用户ID和商品ID删除收藏
     */
    int deleteByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}