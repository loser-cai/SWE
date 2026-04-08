package com.caicai.swe.controller;

import com.caicai.swe.common.Result;
import com.caicai.swe.entity.Favorite;
import com.caicai.swe.mapper.FavoriteMapper;
import com.caicai.swe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏控制器
 */
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private ProductService productService;

    /**
     * 添加收藏
     */
    @PostMapping("/add")
    public Result<Boolean> addFavorite(@RequestParam Long productId) {
        try {
            // TODO: 从session或token中获取当前用户ID
            Long userId = 1L; // 临时使用固定用户ID

            // 检查是否已收藏
            Favorite existFavorite = favoriteMapper.selectByUserIdAndProductId(userId, productId);
            if (existFavorite != null) {
                return Result.error("已收藏该商品");
            }

            // 添加收藏
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setProductId(productId);
            favoriteMapper.insert(favorite);

            // 更新商品收藏次数
            productService.updateFavoriteCount(productId, true);

            return Result.success("收藏成功", true);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/remove")
    public Result<Boolean> removeFavorite(@RequestParam Long productId) {
        try {
            // TODO: 从session或token中获取当前用户ID
            Long userId = 1L; // 临时使用固定用户ID

            int result = favoriteMapper.deleteByUserIdAndProductId(userId, productId);
            if (result > 0) {
                // 更新商品收藏次数
                productService.updateFavoriteCount(productId, false);
            }

            return Result.success("取消收藏成功", result > 0);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户收藏的商品ID列表
     */
    @GetMapping("/list")
    public Result<List<Long>> getFavoriteList() {
        try {
            // TODO: 从session或token中获取当前用户ID
            Long userId = 1L; // 临时使用固定用户ID

            List<Long> productIds = favoriteMapper.selectProductIdsByUserId(userId);
            return Result.success(productIds);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}