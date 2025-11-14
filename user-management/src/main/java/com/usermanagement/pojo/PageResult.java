package com.usermanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long total;          // 总记录数
    private List<T> records;     // 当前页数据
    private Integer currentPage; // 当前页码
    private Integer pageSize;    // 每页大小
}