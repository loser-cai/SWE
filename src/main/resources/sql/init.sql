-- ========================================
-- 校园/社区二手交易vue网页端 - 数据库初始化脚本
-- ========================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS swe CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE swe;

-- ========================================
-- 用户表
-- ========================================
CREATE TABLE IF NOT EXISTS `user`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `password`    varchar(100) NOT NULL COMMENT '密码',
    `nickname`    varchar(50)  DEFAULT NULL COMMENT '昵称',
    `avatar`      varchar(255) DEFAULT NULL COMMENT '头像',
    `phone`       varchar(20)  DEFAULT NULL COMMENT '手机号',
    `email`       varchar(100) DEFAULT NULL COMMENT '邮箱',
    `status`      tinyint      DEFAULT '1' COMMENT '状态 1:正常 0:禁用',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_phone` (`phone`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

-- ========================================
-- 商品表
-- ========================================
CREATE TABLE IF NOT EXISTS `product`
(
    `id`             bigint         NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `user_id`        bigint         NOT NULL COMMENT '发布者ID',
    `title`          varchar(200)   NOT NULL COMMENT '商品标题',
    `description`    text COMMENT '商品描述',
    `price`          decimal(10, 2) NOT NULL COMMENT '价格',
    `category_id`    bigint        DEFAULT NULL COMMENT '分类ID',
    `images`         varchar(1000) DEFAULT NULL COMMENT '商品图片（多张用逗号分隔）',
    `status`         tinyint       DEFAULT '1' COMMENT '状态 1:在售 2:已售出 3:已下架',
    `view_count`     int           DEFAULT '0' COMMENT '浏览次数',
    `favorite_count` int           DEFAULT '0' COMMENT '收藏次数',
    `create_time`    datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品表';

-- ========================================
-- 分类表
-- ========================================
CREATE TABLE IF NOT EXISTS `category`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `name`        varchar(50) NOT NULL COMMENT '分类名称',
    `parent_id`   bigint   DEFAULT '0' COMMENT '父分类ID',
    `sort`        int      DEFAULT '0' COMMENT '排序',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='分类表';

-- ========================================
-- 评论表
-- ========================================
CREATE TABLE IF NOT EXISTS `comment`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `product_id`  bigint       NOT NULL COMMENT '商品ID',
    `user_id`     bigint       NOT NULL COMMENT '评论者ID',
    `content`     varchar(500) NOT NULL COMMENT '评论内容',
    `parent_id`   bigint   DEFAULT '0' COMMENT '父评论ID（0表示一级评论）',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_product_id` (`product_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='评论表';

-- ========================================
-- 收藏表
-- ========================================
CREATE TABLE IF NOT EXISTS `favorite`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id`     bigint NOT NULL COMMENT '用户ID',
    `product_id`  bigint NOT NULL COMMENT '商品ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_product` (`user_id`, `product_id`),
    KEY `idx_product_id` (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='收藏表';

-- ========================================
-- 初始化数据
-- ========================================

-- 插入测试用户
INSERT INTO `user` (`username`, `password`, `nickname`, `phone`, `email`, `status`)
VALUES ('testuser', '123456', '测试用户', '13800138000', 'test@example.com', 1),
       ('admin', 'admin123', '管理员', '13900139000', 'admin@example.com', 1);

-- 插入商品分类
INSERT INTO `category` (`name`, `parent_id`, `sort`)
VALUES ('数码电子', 0, 1),
       ('图书教材', 0, 2),
       ('生活用品', 0, 3),
       ('运动健身', 0, 4),
       ('衣物鞋包', 0, 5),
       ('手机', 1, 1),
       ('电脑', 1, 2),
       ('平板', 1, 3),
       ('考研资料', 2, 1),
       ('教材', 2, 2),
       ('小说', 2, 3);

-- 插入测试商品
INSERT INTO `product` (`user_id`, `title`, `description`, `price`, `category_id`, `images`, `status`, `view_count`,
                       `favorite_count`)
VALUES (1, '二手iPhone 13', '95新，无划痕，配件齐全，保修期内', 3999.00, 5, '', 1, 100, 5),
       (1, '考研数学复习全书', '2024版，全新未使用，附带笔记', 35.00, 10, '', 1, 50, 3),
       (1, '台灯', 'LED护眼台灯，可调节亮度', 45.00, 3, '', 1, 30, 2),
       (2, '篮球', '斯伯丁篮球，使用一年，状况良好', 80.00, 4, '', 1, 20, 1);

-- 插入测试评论
INSERT INTO `comment` (`product_id`, `user_id`, `content`, `parent_id`)
VALUES (1, 2, '还在吗？可以看看实物吗？', 0),
       (1, 1, '在的，可以约时间看', 1),
       (2, 2, '可以便宜点吗？', 0);

-- 插入测试收藏
INSERT INTO `favorite` (`user_id`, `product_id`)
VALUES (2, 1),
       (2, 2),
       (1, 3);

-- ========================================
-- 数据库初始化完成
-- ========================================