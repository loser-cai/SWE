package com.caicai.swe.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品发布DTO
 */
@Data
public class ProductPublishDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private String images;
}