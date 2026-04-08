package com.caicai.swe.service;

import com.caicai.swe.dto.UserLoginDTO;
import com.caicai.swe.dto.UserRegisterDTO;
import com.caicai.swe.entity.User;
import com.caicai.swe.vo.UserVO;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户注册
     */
    Long register(UserRegisterDTO dto);

    /**
     * 用户登录
     */
    UserVO login(UserLoginDTO dto);

    /**
     * 根据ID获取用户信息
     */
    UserVO getUserById(Long id);

    /**
     * 更新用户信息
     */
    Boolean updateUser(User user);

    /**
     * 根据用户名获取用户
     */
    User getUserByUsername(String username);
}