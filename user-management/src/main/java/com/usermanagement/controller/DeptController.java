package com.usermanagement.controller;

import com.usermanagement.pojo.ApiResponse;
import com.usermanagement.pojo.Dept;
import com.usermanagement.service.impl.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
// RestController注解 = Controller + ResponseBodey注解
@RestController
@Slf4j
//@Validated
//@RequiredArgsConstructor
@RequestMapping("/depts")
public class DeptController {
    // 构造器注入,用@Qualifier指定多个接口实现类中的哪个被使用。
    @Qualifier("deptServiceImpl")
    @Autowired
    private DeptService deptService;
    /**
     * 获取所有部门列表
     */
    @GetMapping("/getAll") //限定当前请求方式为Get
    public ResponseEntity<ApiResponse<List<Dept>>> getAllDepts() throws Exception {
        try{
            log.info("接收到获取所有部门列表请求");
            List<Dept> depts = deptService.getAllDepts();
            //System.out.println(depts);
            //IO.println(depts);
            // 数据为空 - 返回成功但数据为空列表
            if (depts == null || depts.isEmpty()) {
                log.info("部门列表为空");
                return ResponseEntity.ok(ApiResponse.success("暂无部门数据", Collections.emptyList()));
            }
            return ResponseEntity.ok(ApiResponse.success(depts));
        } catch (Exception e){
            // 系统异常 - 记录详细日志，返回通用错误
            log.error("获取部门列表系统异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("系统繁忙，请稍后重试"));
        }

    }


    /**
     * 根据ID获取部门
     */
   /* @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Dept>> getDeptById(
            @PathVariable @NotNull(message = "ID不能为空") Integer id) throws Exception {
        log.info("接收到根据ID查询部门请求: {}", id);
        Dept dept = deptService.getDeptById(id);
        if (dept != null) {
            return ResponseEntity.ok(ApiResponse.success(dept));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("部门不存在"));
        }
    }

    *//**
     * 新增部门
     *//*
    @PostMapping
    public ResponseEntity<ApiResponse<Dept>> addDept(@RequestBody @Valid Dept dept) throws Exception {
        log.info("接收到新增部门请求: {}", dept.getName());
        Dept savedDept = deptService.addDept(dept);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("部门创建成功", savedDept));
    }

    *//**
     * 更新部门
     *//*
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Dept>> updateDept(
            @PathVariable Integer id,
            @RequestBody @Valid Dept dept) throws Exception {
        log.info("接收到更新部门请求: {}", id);
        dept.setId(id);
        Dept updatedDept = deptService.updateDept(dept);
        return ResponseEntity.ok(ApiResponse.success("部门更新成功", updatedDept));
    }

    *//**
     * 删除部门
     *//*
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDept(
            @PathVariable @NotNull(message = "ID不能为空") Long id) throws Exception {
        log.info("接收到删除部门请求: {}", id);
        boolean success = deptService.deleteDept(id);
        if (success) {
            return ResponseEntity.ok(ApiResponse.success("部门删除成功"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("部门不存在"));
        }
    }*/
}
