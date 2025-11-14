package com.usermanagement.service.impl.impl;
import com.usermanagement.mapper.EmpMapper;
import com.usermanagement.pojo.Emp;
import com.usermanagement.pojo.LoginInfo;
import com.usermanagement.pojo.PageResult;
import com.usermanagement.service.impl.EmpService;
import com.usermanagement.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.DigestUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//声明bean注解
@Service
@Slf4j
public class EmpServiceImpl implements EmpService {
    //要使用IOC中的bean(Mapper) 依赖注入(DI)
    @Autowired
    private EmpMapper empMapper;

/*
    获取所有员工信息*/

 /*   @Override
    public List<Emp> getAllEmps() throws Exception {
        return empMapper.select();
    }*/

    @Override
    public PageResult<Emp> getEmpsByPage(LocalDate begin, LocalDate end, String gender,
                                         String name, Integer page, Integer pageSize) {

        // 设置分页参数
        PageHelper.startPage(page, pageSize);

        // 执行查询
        List<Emp> empList = empMapper.select(begin, end, gender, name);

        // 获取分页信息
        PageInfo<Emp> pageInfo = new PageInfo<>(empList);

        // 构建返回结果
        return PageResult.<Emp>builder()
                .total(pageInfo.getTotal())
                .records(empList)
                .currentPage(page)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 根据ID获取员工
     */
    public Emp getEmpById(Integer id) throws Exception {
        log.info("根据ID查询员工: {}", id);
        return empMapper.selectEmpbyId(id);
    }

    /**
     * 登录认证
     */
    @Override
    public LoginInfo login(String userName, String password) throws Exception{
        log.info("员工登录: userName={}", userName);
        // 1. 根据用户名查询员工
        Emp emp = empMapper.selectByUserName(userName);
        if (emp == null) {

            throw new RuntimeException("用户名不存在");
        }
        // 2. 密码校验 (使用MD5加密，实际项目中建议使用BCrypt)
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        log.info("md5Password={}", md5Password);
        log.info("emp.getPassword()={}", emp.getPassword());
        if (!md5Password.equals(emp.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        // 3. 生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", emp.getId());
        claims.put("userName", emp.getUserName());
        claims.put("name", emp.getName());

        String token = JwtUtils.generateJwt(claims);
        // 4. 返回员工信息 (密码置空)
        return new LoginInfo(emp.getId(),emp.getUserName(),emp.getName(),token);//构造Logininfo并返回
        //return loginInfo;
    }

    /**
     * 新增员工 - 添加事务管理
     */
    @Transactional(rollbackFor = Exception.class)//所有异常都会回滚。不加默认只有runtime异常才会回滚。
    public Emp addEmp(Emp emp) throws Exception {
        log.info("新增员工: {}", emp.getName());

        emp.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //log.info("员工新增成功，PW: {}",emp.getPassword());
        empMapper.insert(emp);
        log.info("员工新增成功，ID: {}", emp.getId());
        return emp;
    }

    /**
     * 更新员工
     */
    @Transactional
    public Emp updateEmp(Emp emp) throws Exception {
        log.info("更新员工: {}", emp.getId());

        Emp existingEmp = empMapper.selectEmpbyId(emp.getId());
        if (existingEmp == null) {
            throw new RuntimeException("员工不存在: " + emp.getId());
        }

        empMapper.update(emp);
        return empMapper.selectEmpbyId(emp.getId());
    }

    /**
     * 删除员工
     */
    @Transactional
    public boolean deleteEmp(List<Integer> ids) throws Exception {
        log.info("删除员工: {}", ids);
        return empMapper.delete(ids) > 0;
    }

}
