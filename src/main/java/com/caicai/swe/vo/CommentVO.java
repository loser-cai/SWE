package com.caicai.swe.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论视图对象
 */
@Data
public class CommentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long productId;
    private Long userId;
    private String username;
    private String userAvatar;
    private String content;
    private Long parentId;
    private String parentUsername;
    private LocalDateTime createTime;
}