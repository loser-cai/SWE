package com.caicai.swe.mapper;

import com.caicai.swe.entity.Category;
import com.caicai.swe.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类Mapper
 */
@Mapper
public interface CategoryMapper {

    /**
     * 查询所有分类
     */
    List<CategoryVO> selectAll();

    /**
     * 根据ID查询分类
     */
    Category selectById(@Param("id") Long id);

    /**
     * 插入分类
     */
    int insert(Category category);

    /**
     * 更新分类
     */
    int update(Category category);

    /**
     * 根据ID删除分类
     */
    int deleteById(@Param("id") Long id);
}