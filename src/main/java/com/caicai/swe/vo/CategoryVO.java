package com.caicai.swe.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类视图对象
 */
@Data
public class CategoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private LocalDateTime createTime;
}