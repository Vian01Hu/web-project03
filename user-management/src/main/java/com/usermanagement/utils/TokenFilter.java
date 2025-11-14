package com.usermanagement.utils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")  // 拦截所有请求  注释掉就不可用了。使用需打开此注释。
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. 获取请求url
        String requestURL = request.getRequestURL().toString();
        log.info("TokenFilter启动....");
        log.info("请求URL: {}", requestURL);

        // 2. 判断请求url中是否包含login，如果包含，说明是登录操作，放行
        if (isLoginRequest(requestURL)) {
            log.info("登录请求，放行");
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 获取请求头中的令牌（token）
        String token = getTokenFromRequest(request);
        log.info("获取到的token: {}", token);

        // 4. 判断令牌是否存在，如果不存在，响应401
        if (!StringUtils.hasText(token)) {
            log.warn("Token不存在，返回401");
            sendUnauthorizedResponse(response, "令牌不存在");
            return;
        }

        // 5. 解析token，如果解析失败，响应401
        try {
            Claims claims = JwtUtils.parseJwt(token);
            log.info("Token解析成功，用户: {}", claims.get("userName"));

            // 可以将用户信息存入请求域，供后续使用
            request.setAttribute("userId", claims.get("id"));
            request.setAttribute("userName", claims.get("userName"));

        } catch (Exception e) {
            log.error("Token解析失败: {}", e.getMessage());
            sendUnauthorizedResponse(response, "令牌无效或已过期");
            return;
        }

        // 6. 放行
        log.info("Token校验通过，放行请求");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 判断是否是登录请求
     */
    private boolean isLoginRequest(String requestURL) {
        // 可以根据实际需求调整登录路径的判断逻辑
        return requestURL.contains("/login") ||
                requestURL.endsWith("/api/login") ||
                requestURL.contains("/auth/login");
    }

    /**
     * 从请求头中获取token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // 从Authorization头获取token
        String authHeader = request.getHeader("token");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("token ")) {
            return authHeader.substring(7); // 去掉"Bearer "前缀
        }

        // 也可以从其他位置获取token，比如参数
        String tokenParam = request.getParameter("token");
        if (StringUtils.hasText(tokenParam)) {
            return tokenParam;
        }

        return null;
    }

    /**
     * 发送401未授权响应
     */
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String jsonResponse = String.format(
                "{\"code\": 401, \"msg\": \"%s\", \"data\": null}",
                message
        );

        response.getWriter().write(jsonResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("JWT认证过滤器初始化");
    }

    @Override
    public void destroy() {
        log.info("JWT认证过滤器销毁");
    }
}
