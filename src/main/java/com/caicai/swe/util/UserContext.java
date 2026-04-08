package com.caicai.swe.util;

/**
 * 用户上下文 - 使用 ThreadLocal 存储当前登录用户信息
 */
public class UserContext {

    private static final ThreadLocal<Long> USER_ID_HOLDER = new ThreadLocal<>();

    /**
     * 设置当前用户 ID
     */
    public static void setUserId(Long userId) {
        USER_ID_HOLDER.set(userId);
    }

    /**
     * 获取当前用户 ID
     */
    public static Long getUserId() {
        return USER_ID_HOLDER.get();
    }

    /**
     * 清除当前用户 ID
     */
    public static void clear() {
        USER_ID_HOLDER.remove();
    }

    /**
     * 检查是否有用户登录
     */
    public static boolean hasUser() {
        return USER_ID_HOLDER.get() != null;
    }
}