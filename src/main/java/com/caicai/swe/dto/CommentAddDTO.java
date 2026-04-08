package com.caicai.swe.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 评论添加DTO
 */
@Data
public class CommentAddDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long productId;
    private String content;
    private Long parentId;
}