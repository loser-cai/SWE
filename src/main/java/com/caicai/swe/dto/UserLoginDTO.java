package com.caicai.swe.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录DTO
 */
@Data
public class UserLoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}