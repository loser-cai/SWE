package com.caicai.swe.controller;

import com.caicai.swe.common.Result;
import com.caicai.swe.entity.Category;
import com.caicai.swe.mapper.CategoryMapper;
import com.caicai.swe.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类控制器
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取所有分类
     */
    @GetMapping("/list")
    public Result<List<CategoryVO>> getAllCategories() {
        try {
            List<CategoryVO> list = categoryMapper.selectAll();
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 添加分类
     */
    @PostMapping("/add")
    public Result<Long> addCategory(@RequestBody Category category) {
        try {
            categoryMapper.insert(category);
            return Result.success("添加成功", category.getId());
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新分类
     */
    @PutMapping("/update")
    public Result<Boolean> updateCategory(@RequestBody Category category) {
        try {
            int result = categoryMapper.update(category);
            return Result.success("更新成功", result > 0);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteCategory(@PathVariable Long id) {
        try {
            int result = categoryMapper.deleteById(id);
            return Result.success("删除成功", result > 0);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}