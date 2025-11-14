package com.usermanagement.service.impl;

import com.usermanagement.pojo.Emp;
import com.usermanagement.pojo.LoginInfo;
import com.usermanagement.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    /*
     * 获取所有员工信息
     * */
    /*public List<Emp> getAllEmps() throws Exception;// 抽象方法，只有声明，没有实现。规定：必须有一个返回员工列表的方法。
*/
    /**
     * 条件分页查询员工
     */
    PageResult<Emp> getEmpsByPage(LocalDate begin, LocalDate end, String gender,
                                  String name, Integer page, Integer pageSize);
    /**
     * 根据ID获取员工
     */
     public Emp getEmpById(Integer id) throws Exception;

    /**
     * 登录认证
     */
    public LoginInfo login(String userName, String password) throws Exception;

     /**
     * 新增员工 - 添加事务管理
     */
    public Emp addEmp(Emp Emp) throws Exception;

    /**
     * 更新员工
     */
    public Emp updateEmp(Emp Emp) throws Exception;

    /**
     * 删除员工
     */
    public boolean deleteEmp(List<Integer> ids) throws Exception;
}
