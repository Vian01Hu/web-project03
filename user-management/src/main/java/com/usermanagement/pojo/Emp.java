package com.usermanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/* get,set方法和toString,都可以用Data标签实现 */
@Data
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Emp {
    private Integer id; // ID, 主键
    private String userName; // 用户名
    private String password; // 密码
    private String name; // 姓名
    private Integer gender; // 性别, 1:男, 2:女
    private String image; // 头像
    private LocalDate entryDate; // 入职日期
    private String deptCode; // 关联的部门ID
    private LocalDateTime createTime; // 创建时间
    private LocalDateTime updateTime; // 修改时间
    private String deptName;
}
