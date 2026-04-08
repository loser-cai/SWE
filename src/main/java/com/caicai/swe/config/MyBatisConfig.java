package com.caicai.swe.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 配置
 */
@Configuration
@MapperScan("com.caicai.swe.mapper")
public class MyBatisConfig {
}