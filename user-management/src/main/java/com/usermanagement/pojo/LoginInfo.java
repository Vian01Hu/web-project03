package com.usermanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
封装登录结果信息
 */
@Data
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class LoginInfo {
    private Integer id; // ID,
    private String userName; // 用户名
    private String name; // 姓名
    private String token;//令牌
}
