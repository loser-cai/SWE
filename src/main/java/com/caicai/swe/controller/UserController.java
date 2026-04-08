package com.caicai.swe.controller;

import com.caicai.swe.common.Result;
import com.caicai.swe.dto.UserLoginDTO;
import com.caicai.swe.dto.UserRegisterDTO;
import com.caicai.swe.entity.User;
import com.caicai.swe.service.UserService;
import com.caicai.swe.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Long> register(@RequestBody UserRegisterDTO dto) {
        try {
            Long userId = userService.register(dto);
            return Result.success("注册成功", userId);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody UserLoginDTO dto) {
        try {
            UserVO userVO = userService.login(dto);
            return Result.success("登录成功", userVO);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info/{id}")
    public Result<UserVO> getUserInfo(@PathVariable Long id) {
        try {
            UserVO userVO = userService.getUserById(id);
            return Result.success(userVO);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/update")
    public Result<Boolean> updateUser(@RequestBody User user) {
        try {
            Boolean result = userService.updateUser(user);
            return Result.success("更新成功", result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}