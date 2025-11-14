package com.usermanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* get,set方法和toString,都可以用Data标签实现 */
@Data
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Dept {
    private String code; //推荐使用包装类型而非基本类型，因为基本类型有默认值。
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
