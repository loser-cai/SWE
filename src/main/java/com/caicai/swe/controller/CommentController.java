package com.caicai.swe.controller;

import com.caicai.swe.common.PageResult;
import com.caicai.swe.common.Result;
import com.caicai.swe.dto.CommentAddDTO;
import com.caicai.swe.service.CommentService;
import com.caicai.swe.util.UserContext;
import com.caicai.swe.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     */
    @PostMapping("/add")
    public Result<Long> addComment(@RequestBody CommentAddDTO dto) {
        try {
            Long userId = UserContext.getUserId();
            Long commentId = commentService.addComment(dto, userId);
            return Result.success("评论成功", commentId);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取商品评论列表
     */
    @GetMapping("/list")
    public Result<PageResult<CommentVO>> getCommentList(@RequestParam Long productId,
                                                          @RequestParam(defaultValue = "1") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        try {
            PageResult<CommentVO> pageResult = commentService.getCommentList(productId, page, size);
            return Result.success(pageResult);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteComment(@PathVariable Long id) {
        try {
            Boolean result = commentService.deleteComment(id);
            return Result.success("删除成功", result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}