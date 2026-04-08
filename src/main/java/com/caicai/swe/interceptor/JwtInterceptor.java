package com.caicai.swe.interceptor;

import com.caicai.swe.util.JwtUtil;
import com.caicai.swe.util.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 拦截器 - 解析 Token 并设置用户上下文
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
        // 对于 OPTIONS 预检请求，直接放行
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            return true;
        }

        // 对于公开接口（不需要登录），不进行拦截
        String uri = request.getRequestURI();
        if(uri.contains("/user/login") || uri.contains("/user/register") 
                || uri.contains("/category/list") || uri.contains("/product/list")
                || uri.contains("/product/search") || uri.equals("/product") ) {  // 只允许查看商品详情，不允许更新/删除
            return true;
        }

        // 获取 Authorization Header
        String authHeader = request.getHeader("Authorization");

        // 提取 Token
        String token = jwtUtil.extractTokenFromHeader(authHeader);

        // 验证 Token 并设置用户上下文
        if(token != null && jwtUtil.validateToken(token)){
            Long userId = jwtUtil.getUserIdFromToken(token);
            if(userId != null){
                UserContext.setUserId(userId);
                return true;
            }
        }

        // Token 无效或未提供，返回 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"未授权，请先登录\"}");
        log.warn("forbidden request to {} with invalid token",uri);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex) throws Exception {
        // 清理 ThreadLocal，防止内存泄漏
        UserContext.clear();
    }
}