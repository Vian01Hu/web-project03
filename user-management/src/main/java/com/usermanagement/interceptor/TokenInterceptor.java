package com.usermanagement.interceptor;
import com.usermanagement.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 令牌校验拦截器
 */

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 1. 获取请求url
    String requestURL = request.getRequestURL().toString();
    String requestURI = request.getRequestURI();
    log.info("拦截器 - 请求URL: {}, URI: {}", requestURL, requestURI);

    // 2. 判断请求url中是否包含login，如果包含，说明是登录操作，放行
    /*if (requestURI.contains("/login") || requestURL.contains("/upload")) {
        log.info("拦截器 - {} 请求，放行",requestURL.substring(requestURL.lastIndexOf("/") + 1));
        return true;
    }*/

    // 3. 获取请求头中的令牌（token）
    String token = getTokenFromRequest(request);
    log.info("拦截器 - 获取到的token: {}", token);

    // 4. 判断令牌是否存在，如果不存在，响应401 Status
    if (!StringUtils.hasText(token)) {
        log.warn("拦截器 - Token不存在，返回401");
        response.setStatus(401);
        //sendUnauthorizedResponse(response, "令牌不存在");
        return false;
    }

    // 5. 解析token，如果解析失败，响应401
    try {
        Claims claims = JwtUtils.parseJwt(token);
        String userName = (String) claims.get("userName");
        Integer userId = (Integer) claims.get("id");

        log.info("拦截器 - Token解析成功，用户ID: {}, 用户名: {}", userId, userName);

        // 可以将用户信息存入请求域，供后续使用
        request.setAttribute("userId", userId);
        request.setAttribute("userName", userName);

    } catch (Exception e) {
        log.error("拦截器 - Token解析失败: {}", e.getMessage());
        sendUnauthorizedResponse(response, "令牌无效或已过期");
        return false;
    }

    // 6. 放行
    log.info("拦截器 - Token校验通过，放行请求");
    return true;
}

/**
 * 从请求头中获取token
 */
private String getTokenFromRequest(HttpServletRequest request) {
    // 从"token""头获取token
    String authHeader = request.getHeader("token");
    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
        return authHeader.substring(7); // 去掉"Bearer  "前缀
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
private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws Exception {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json;charset=UTF-8");

    String jsonResponse = String.format(
            "{\"code\": 401, \"msg\": \"%s\", \"data\": null}",
            message
    );

    response.getWriter().write(jsonResponse);
    response.getWriter().flush();
}

@Override
public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    // 请求完成后清理资源（如果需要）
    log.debug("拦截器 - 请求完成: {}", request.getRequestURI());
}
}
