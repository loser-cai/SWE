package com.caicai.swe.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册DTO
 */
@Data
public class UserRegisterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String phone;
    private String email;
    private String nickname;
}