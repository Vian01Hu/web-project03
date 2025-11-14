package com.usermanagement.service.impl;

import com.usermanagement.pojo.Dept;

import java.util.List;

public interface DeptService {
    /*
     * 获取所有部门信息
     * */
    public List<Dept> getAllDepts() throws Exception;// 抽象方法，只有声明，没有实现。规定：必须有一个返回部门列表的方法。

    /**
     * 根据ID获取部门
     */
   /* public Dept getDeptById(Integer id) throws Exception;

    *//**
     * 新增部门 - 添加事务管理
     *//*
    public Dept addDept(Dept dept) throws Exception;

    *//**
     * 更新部门
     *//*
    public Dept updateDept(Dept dept) throws Exception;

    *//**
     * 删除部门
     *//*
    public boolean deleteDept(Long id) throws Exception;*/


}
