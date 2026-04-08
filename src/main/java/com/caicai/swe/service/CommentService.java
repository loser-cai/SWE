package com.caicai.swe.service;

import com.caicai.swe.common.PageResult;
import com.caicai.swe.dto.CommentAddDTO;
import com.caicai.swe.vo.CommentVO;

/**
 * 评论服务接口
 */
public interface CommentService {

    /**
     * 添加评论
     */
    Long addComment(CommentAddDTO dto, Long userId);

    /**
     * 获取商品评论列表（分页）
     */
    PageResult<CommentVO> getCommentList(Long productId, Integer page, Integer size);

    /**
     * 删除评论
     */
    Boolean deleteComment(Long id);
}