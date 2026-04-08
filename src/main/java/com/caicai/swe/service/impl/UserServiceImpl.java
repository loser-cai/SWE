package com.caicai.swe.service.impl;

import com.caicai.swe.common.ResultCode;
import com.caicai.swe.dto.UserLoginDTO;
import com.caicai.swe.dto.UserRegisterDTO;
import com.caicai.swe.entity.User;
import com.caicai.swe.mapper.UserMapper;
import com.caicai.swe.service.UserService;
import com.caicai.swe.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long register(UserRegisterDTO dto) {
        // 校验用户名是否已存在
        User existUser = userMapper.selectByUsername(dto.getUsername());
        if (existUser != null) {
            throw new RuntimeException(ResultCode.USER_ALREADY_EXIST.getMessage());
        }

        // 校验手机号是否已存在
        if (StringUtils.isNotBlank(dto.getPhone())) {
            existUser = userMapper.selectByPhone(dto.getPhone());
            if (existUser != null) {
                throw new RuntimeException("手机号已被注册");
            }
        }

        // 校验邮箱是否已存在
        if (StringUtils.isNotBlank(dto.getEmail())) {
            existUser = userMapper.selectByEmail(dto.getEmail());
            if (existUser != null) {
                throw new RuntimeException("邮箱已被注册");
            }
        }

        // 创建用户
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword()); // 实际项目中应该加密
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setNickname(dto.getNickname() != null ? dto.getNickname() : dto.getUsername());
        user.setStatus(1);

        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public UserVO login(UserLoginDTO dto) {
        User user = userMapper.selectByUsername(dto.getUsername());
        if (user == null) {
            throw new RuntimeException(ResultCode.USER_NOT_EXIST.getMessage());
        }

        if (!dto.getPassword().equals(user.getPassword())) {
            throw new RuntimeException(ResultCode.PASSWORD_ERROR.getMessage());
        }

        if (user.getStatus() != 1) {
            throw new RuntimeException("用户已被禁用");
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public UserVO getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException(ResultCode.USER_NOT_EXIST.getMessage());
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public Boolean updateUser(User user) {
        int result = userMapper.update(user);
        return result > 0;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
}