package com.caicai.swe.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品视图对象
 */
@Data
public class ProductVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long userId;
    private String username;
    private String userAvatar;
    private String title;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private String categoryName;
    private String images;
    private Integer status;
    private Integer viewCount;
    private Integer favoriteCount;
    private LocalDateTime createTime;
}