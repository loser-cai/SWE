package com.caicai.swe.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类实体
 */
@Data
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}