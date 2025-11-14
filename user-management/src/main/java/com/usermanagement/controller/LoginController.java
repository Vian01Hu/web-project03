package com.usermanagement.controller;

import com.usermanagement.pojo.ApiResponse;
import com.usermanagement.pojo.Emp;
import com.usermanagement.pojo.LoginInfo;
import com.usermanagement.service.impl.EmpService;
import com.usermanagement.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private EmpService empService;

    /**
     * 员工登录
     */
    @PostMapping
    public ApiResponse<LoginInfo> login(@RequestBody Emp emp) {
        log.info("接收到员工登录请求: {}", emp.getUserName());

        try {
            // 调用service进行登录认证
            LoginInfo loginInfo = empService.login(emp.getUserName(), emp.getPassword());
            // 响应数据
            log.info("登录成功：{}", loginInfo);
            return ApiResponse.success("登录成功", loginInfo);

        } catch (RuntimeException e) {
            log.warn("登录业务异常: {}", e.getMessage());
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            log.warn("登录系统异常: {}", e.getMessage());
            return ApiResponse.error("系统异常，请稍后重试");
        }
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/userInfo")
    public ApiResponse getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            // 从token中解析用户ID
            Integer userId = JwtUtils.parseJwt(token.replace("Bearer ", "")).get("id", Integer.class);
            Emp emp = empService.getEmpById(userId);
            emp.setPassword(null); // 密码置空
            return ApiResponse.success(emp);
        } catch (Exception e) {
            return ApiResponse.error("获取用户信息失败");
        }
    }


}
