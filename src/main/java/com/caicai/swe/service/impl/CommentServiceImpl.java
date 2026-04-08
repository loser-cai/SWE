package com.caicai.swe.service.impl;

import com.caicai.swe.common.PageResult;
import com.caicai.swe.common.ResultCode;
import com.caicai.swe.dto.CommentAddDTO;
import com.caicai.swe.entity.Comment;
import com.caicai.swe.mapper.CommentMapper;
import com.caicai.swe.service.CommentService;
import com.caicai.swe.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论服务实现
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Long addComment(CommentAddDTO dto, Long userId) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(dto, comment);
        comment.setUserId(userId);
        commentMapper.insert(comment);
        return comment.getId();
    }

    @Override
    public PageResult<CommentVO> getCommentList(Long productId, Integer page, Integer size) {
        // 计算偏移量
        int offset = (page - 1) * size;

        // 查询列表
        List<CommentVO> list = commentMapper.selectListByProductId(productId, offset, size);

        // 查询总数
        Long total = commentMapper.selectCountByProductId(productId);

        return PageResult.of(total, list);
    }

    @Override
    public Boolean deleteComment(Long id) {
        int result = commentMapper.deleteById(id);
        return result > 0;
    }
}