package com.caicai.swe.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论实体
 */
@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long productId;
    private Long userId;
    private String content;
    private Long parentId;
    private LocalDateTime createTime;
}