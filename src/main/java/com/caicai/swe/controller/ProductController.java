package com.caicai.swe.controller;

import com.caicai.swe.common.PageResult;
import com.caicai.swe.common.Result;
import com.caicai.swe.dto.ProductPublishDTO;
import com.caicai.swe.dto.ProductQueryDTO;
import com.caicai.swe.entity.Product;
import com.caicai.swe.service.ProductService;
import com.caicai.swe.util.UserContext;
import com.caicai.swe.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 发布商品
     */
    @PostMapping("/publish")
    public Result<Long> publish(@RequestBody ProductPublishDTO dto) {
        try {
            Long userId = UserContext.getUserId();
            Long productId = productService.publish(dto, userId);
            return Result.success("发布成功", productId);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取商品详情
     */
    @GetMapping("/{id}")
    public Result<ProductVO> getProductById(@PathVariable Long id) {
        try {
            ProductVO productVO = productService.getProductById(id);
            // 增加浏览次数
            productService.incrementViewCount(id);
            return Result.success(productVO);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取商品列表（分页）
     */
    @GetMapping("/list")
    public Result<PageResult<ProductVO>> getProductList(ProductQueryDTO dto) {
        try {
            PageResult<ProductVO> pageResult = productService.getProductList(dto);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 搜索商品
     */
    @GetMapping("/search")
    public Result<PageResult<ProductVO>> searchProducts(@RequestParam String keyword,
                                                          @RequestParam(defaultValue = "1") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        try {
            ProductQueryDTO dto = new ProductQueryDTO();
            dto.setKeyword(keyword);
            dto.setPage(page);
            dto.setSize(size);
            dto.setStatus(1); // 只查询在售商品
            PageResult<ProductVO> pageResult = productService.getProductList(dto);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新商品
     */
    @PutMapping("/update")
    public Result<Boolean> updateProduct(@RequestBody Product product) {
        try {
            Boolean result = productService.updateProduct(product);
            return Result.success("更新成功", result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除商品
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteProduct(@PathVariable Long id) {
        try {
            Boolean result = productService.deleteProduct(id);
            return Result.success("删除成功", result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}