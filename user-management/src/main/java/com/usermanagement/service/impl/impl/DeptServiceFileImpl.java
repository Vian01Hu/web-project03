package com.usermanagement.service.impl.impl;

import com.usermanagement.dao.DeptDao;
import com.usermanagement.pojo.Dept;
import com.usermanagement.service.impl.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DeptServiceFileImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    /*
     * 获取所有部门信息
     * */
    @Override
    public List<Dept> getAllDepts() throws Exception {
        //调用DAO获取数据
        List<String> lines = deptDao.getAll();

        //解析文本数据，将其封装为对象，多个对象封装到集合中
        List<Dept> deptList = lines.stream().map(line -> {

            String[] split = line.split(",");
            String code = split[0];
            String name = split[1];
            LocalDateTime creatTime = LocalDateTime.parse(split[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime updateTime = LocalDateTime.parse(split[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return new Dept(code, name, creatTime, updateTime);
        }).toList();
        return deptList;
    }
}


