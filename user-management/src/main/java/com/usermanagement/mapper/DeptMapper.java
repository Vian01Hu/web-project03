
package com.usermanagement.mapper;

import com.usermanagement.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
* 部门管理的Mapper接口
* */


@Mapper
public interface DeptMapper {

/*
    * 查询所有部门数据
    * */

    @Select("select * from dept")
    @Results({
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<Dept> selectAll();


/**
     * 根据ID查询部门
     *//*

  */
/*  @Select("SELECT * FROM dept WHERE id = #{id}")
    public Dept selectDeptbyId(Integer id);

    *//*
*/
/*
    * 查询是否有同名部门存在
    * *//*
*/
/*
    @Select("SELECT * FROM dept WHERE name = #{name}")
    public List<Dept> selectDeptByName(String name);

    *//*
*/
/**
     * 新增部门 - 返回自增主键
     *//*
*/
/*
    @Insert("INSERT INTO dept ( name, create_time, update_time) VALUES (#{deptName}, NOW()，NOW()")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Dept dept);

    *//*
*/
/**
     * 更新部门
     *//*
*/
/*
    @Update("UPDATE dept SET dept_name = #{deptName}, update_time = NOW() WHERE id = #{id}")
    int update(Dept dept);

    *//*
*/
/**
     * 删除部门
     *//*
*/
/*
    @Delete("DELETE FROM dept WHERE id = #{id}")
    int delete(Long id);*/

}

