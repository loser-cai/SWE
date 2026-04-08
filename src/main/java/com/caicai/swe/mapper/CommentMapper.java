package com.caicai.swe.mapper;

import com.caicai.swe.entity.Comment;
import com.caicai.swe.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评论Mapper
 */
@Mapper
public interface CommentMapper {

    /**
     * 根据ID查询评论
     */
    Comment selectById(@Param("id") Long id);

    /**
     * 分页查询商品评论列表
     */
    List<CommentVO> selectListByProductId(@Param("productId") Long productId,
                                          @Param("offset") Integer offset,
                                          @Param("limit") Integer limit);

    /**
     * 查询商品评论总数
     */
    Long selectCountByProductId(@Param("productId") Long productId);

    /**
     * 插入评论
     */
    int insert(Comment comment);

    /**
     * 根据ID删除评论
     */
    int deleteById(@Param("id") Long id);
}