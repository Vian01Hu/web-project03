package com.usermanagement.service.impl.impl;
import com.usermanagement.mapper.DeptMapper;
import com.usermanagement.pojo.Dept;
import com.usermanagement.service.impl.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//声明bean注解
@Service
@Slf4j
public class DeptServiceImpl implements DeptService {
    //要使用IOC中的bean(Mapper) 依赖注入(DI)
    @Autowired
    private DeptMapper deptMapper;

/*
    获取所有部门信息*/

    @Override
    public List<Dept> getAllDepts() throws Exception {
        return deptMapper.selectAll();
    }
    /**
     * 根据ID获取部门
     */
   /* public Dept getDeptById(Integer id) throws Exception {
        log.info("根据ID查询部门: {}", id);
        return deptMapper.selectDeptbyId(id);
    }

    *//**
     * 新增部门 - 添加事务管理
     *//*
    @Transactional
    public Dept addDept(Dept dept) throws Exception {
        log.info("新增部门: {}", dept.getName());

        // 检查部门名称是否已存在
        List<Dept> existingDepts = deptMapper.selectDeptByName(dept.getName());
        if (existingDepts.size() > 0) {
            throw new RuntimeException("部门编码已存在: " + dept.getName());
        }
        deptMapper.insert(dept);
        log.info("部门新增成功，ID: {}", dept.getId());
        return dept;
    }

    *//**
     * 更新部门
     *//*
    @Transactional
    public Dept updateDept(Dept dept) throws Exception {
        log.info("更新部门: {}", dept.getId());

        Dept existingDept = deptMapper.selectDeptbyId(dept.getId());
        if (existingDept == null) {
            throw new RuntimeException("部门不存在: " + dept.getId());
        }

        deptMapper.update(dept);
        return deptMapper.selectDeptbyId(dept.getId());
    }

    *//**
     * 删除部门
     *//*
    @Transactional
    public boolean deleteDept(Long id) throws Exception {
        log.info("删除部门: {}", id);
        return deptMapper.delete(id) > 0;
    }*/
//接口另一种用法：在测试 DeptService 时，你可以轻松创建一个 DeptDao 的模拟实现（Mock），而不是去连接真实的数据库。
//    DeptDao deptDao = new DeptDao() {
//        @Override
//        public List<String> list() throws Exception {
//            List<String> list = new ArrayList<String>();
//            list.add("1");
//            return list;
//        }
//    };


}
