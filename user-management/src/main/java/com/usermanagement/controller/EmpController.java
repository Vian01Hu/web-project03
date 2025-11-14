package com.usermanagement.controller;

import com.usermanagement.pojo.ApiResponse;
import com.usermanagement.pojo.Emp;
import com.usermanagement.pojo.PageResult;
import com.usermanagement.service.impl.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

// RestController注解 = Controller + ResponseBodey注解
@RestController
@Slf4j
//@Validated
//@RequiredArgsConstructor
@RequestMapping("/emps")

public class EmpController {
    // 构造器注入,用@Qualifier指定多个接口实现类中的哪个被使用。
    @Autowired
    private EmpService empService;

    /**
     * 获取所有员工列表
     */
    @GetMapping //限定当前请求方式为Get
    public ResponseEntity<ApiResponse<PageResult<Emp>>> getEmpsByPage(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer pageSize) throws Exception {
        try {
            log.info("接收到分页查询员工数据请求: begin={}, end={}, gender={}, name={}, page={}, pageSize={}",
                    begin, end, gender, name, page, pageSize);
            if ("undefined".equals(gender)) {
                gender = "";
                log.info("gender:{}",gender);
            }
            PageResult<Emp> emps = empService.getEmpsByPage(begin, end, gender, name, page, pageSize);
            if (emps.getTotal() == 0) {
                log.info("未查询到员工数据。");
                return ResponseEntity.ok(ApiResponse.success("暂无员工数据", emps));
            }
            return ResponseEntity.ok(ApiResponse.success(emps));
        } catch (Exception e) {
            // 系统异常 - 记录详细日志，返回通用错误
            log.error("获取员工列表系统异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("系统繁忙，请稍后重试"));
        }
    }

    /**
     * 根据ID获取员工信息 用于页面回显
     *
     * @param id 员工ID
     * @return 员工详细信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Emp>> getEmpById(@PathVariable Integer id) {
        try {
            log.info("根据ID查询员工: id={}", id);
            Emp emp = empService.getEmpById(id);

            if (emp == null) {
                log.warn("员工不存在: id={}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("员工不存在"));
            }

            log.info("查询结果：{}", emp);
            return ResponseEntity.ok(ApiResponse.success("查询成功", emp));

        } catch (Exception e) {
            log.error("查询员工系统异常: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("系统繁忙，请稍后重试"));
        }
    }

    /**
     * 新增员工 请求路径为类的请求路径:/emps
     * Json封装的对象需要加RequestBody注解
     *
     * @param emp 员工信息
     * @return 新增的员工信息
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Emp>> addEmp(@RequestBody Emp emp) {
        try {
            log.info("新增员工: name={}", emp.getName());

            // 基本参数校验
            if (emp.getName() == null || emp.getName().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("员工姓名不能为空"));
            }

            Emp savedEmp = empService.addEmp(emp);
            log.info("员工新增成功: id={}, name={}", savedEmp.getId(), savedEmp.getName());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("员工创建成功", savedEmp));

        } catch (Exception e) {
            log.error("新增员工系统异常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("创建员工失败，请稍后重试"));
        }
    }

    /**
     * 更新员工信息
     *
     * @param emp 更新后的员工信息
     * @return 更新后的员工信息
     */
    @PutMapping
    public ResponseEntity<ApiResponse<Emp>> updateEmp(@RequestBody Emp emp) {
        try {
            log.info("更新员工信息emp: ", emp);
            Emp updatedEmp = empService.updateEmp(emp);

            if (updatedEmp == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("员工不存在，更新失败"));
            }

            log.info("员工更新成功: name={}", emp.getName());
            return ResponseEntity.ok(ApiResponse.success("员工信息更新成功", updatedEmp));

        } catch (Exception e) {
            log.error("更新员工系统异常: id={}", emp.getId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("更新员工失败，请稍后重试"));
        }
    }

    /**
     * 批量删除员工 请求路径:/emps
     *
     * @param ids 员工ID数组 使用集合形式接收数组,使用@RequestParam注解
     * @return 删除结果
     */
    @DeleteMapping()
    public ResponseEntity<ApiResponse<Boolean>> deleteEmp(@RequestParam List<Integer> ids) throws Exception {

        try {
            log.info("删除员工: ids={}", ids);

            boolean result = empService.deleteEmp(ids);

            if (result) {
                log.info("员工删除成功: id={}", ids);
                return ResponseEntity.ok(ApiResponse.success("员工删除成功", true));
            } else {
                log.warn("员工删除失败: id={}", ids);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("员工不存在，删除失败"));
            }

        } catch (Exception e) {
            log.error("删除员工系统异常: id={}", ids, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("删除员工失败，请稍后重试"));
        }
    }

    /**
     * 批量删除员工 请求路径:/emps
     * @param ids 员工ID数组
     * @return 删除结果
     */
  /*  @DeleteMapping()
    public ResponseEntity<ApiResponse<Boolean>> deleteEmp(@PathVariable Integer[] ids) {
        try {
            log.info("批量删除员工: ids={}", Arrays.toString(ids));

            boolean result = empService.deleteEmp(id);

            if (result) {
                log.info("员工删除成功: id={}", id);
                return ResponseEntity.ok(ApiResponse.success("员工删除成功", true));
            } else {
                log.warn("员工删除失败: id={}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("员工不存在，删除失败"));
            }

        } catch (Exception e) {
            log.error("删除员工系统异常: id={}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("删除员工失败，请稍后重试"));
        }
    }
    */
}

