
package com.usermanagement.mapper;

import com.usermanagement.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

/*
 * 员工数据的Mapper接口
 * */


@Mapper
public interface EmpMapper {

    /*
     * 查询所有员工数据
     * */

   /* @Select("select emp.* , dept.name as dept_name  from emp  left join dept on emp.dept_code=dept.code")
    @Results({
            @Result(column = "user_name", property = "userName"),
            @Result(column = "dept_code", property = "deptCode"),
            @Result(column = "entry_date", property = "entryDate"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "dept_name", property = "deptName")
    })
    List<Emp> selectAll();*/

    @Select("<script>" +
            "SELECT emp.*, dept.name as dept_name FROM emp " +
            "LEFT JOIN dept ON emp.dept_code = dept.code " +
            "WHERE 1=1 " +
            "<if test='begin != null'> AND entry_date &gt;= #{begin} </if>" +
            "<if test='end != null'> AND entry_date &lt;= #{end} </if>" +
            "<if test='gender != null and gender != \"\"'> AND gender = #{gender} </if>" +
            "<if test='name != null and name != \"\"'> AND emp.name LIKE CONCAT('%', #{name}, '%') </if>" +
            "ORDER BY emp.id" +
            "</script>")
    @Results({
            @Result(column = "user_name", property = "userName"),
            @Result(column = "dept_code", property = "deptCode"),
            @Result(column = "entry_date", property = "entryDate"),
            @Result(column = "dept_name", property = "deptName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    List<Emp> select(@Param("begin") LocalDate begin,
                                @Param("end") LocalDate end,
                                @Param("gender") String gender,
                                @Param("name") String name);


    /**
     * 根据ID查询员工
     */
    @Select("select emp.* , dept.name as dept_name from emp  left " +
            "join dept on emp.dept_code=dept.code WHERE emp.id = #{id}")
    @Results({
            @Result(column = "user_name", property = "userName"),
            @Result(column = "dept_code", property = "deptCode"),
            @Result(column = "entry_date", property = "entryDate"),
            @Result(column = "dept_name", property = "deptName"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    public Emp selectEmpbyId(Integer id);

    /**
     * 登录用：根据用户名查询员工
     */
    @Select("SELECT emp.* FROM emp  WHERE emp.user_name = #{userName}")
    @Results({
            @Result(column = "user_name", property = "userName"),
            @Result(column = "dept_code", property = "deptCode"),
            @Result(column = "entry_date", property = "entryDate"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    Emp selectByUserName(String userName);

    /**
     * 新增员工 - 返回自增主键
     */
    @Insert("INSERT INTO Emp ( user_name, password,name, gender, image, dept_code, entry_date, create_time, update_time) " +
            "VALUES (#{userName},#{password},#{name},#{gender},#{image},#{deptCode},#{entryDate}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
//需要获取数据库的自增主键，并返回给emp对象的id属性
    int insert(Emp emp);

    /**
     * 更新员工
     */

    @Update("UPDATE Emp SET user_name = #{userName},name = #{name},gender = #{gender},image = #{image}," +
            "dept_code = #{deptCode}, entry_date = #{entryDate},update_time = NOW() WHERE id = #{id}")
    int update(Emp emp);

    /**
     * 删除员工
     */

    @Delete("<script>" +
            "DELETE FROM Emp WHERE id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>#{id}</foreach>" +
            "</script>")
    int delete(List<Integer> ids);
}

