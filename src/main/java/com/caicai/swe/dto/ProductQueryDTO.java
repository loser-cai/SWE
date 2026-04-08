package com.caicai.swe.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品查询DTO
 */
@Data
public class ProductQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer size = 10;
    private String keyword;
    private Long categoryId;
    private Integer status;
    private Long userId;
}