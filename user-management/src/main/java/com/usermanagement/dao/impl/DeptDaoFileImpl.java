package com.usermanagement.dao.impl;

import com.usermanagement.dao.DeptDao;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

/*从txt文件中获取部门数据*/
@Repository
public class DeptDaoFileImpl implements DeptDao {
    public List<String> getAll() throws Exception {
        // 加载resource路径下的depts.txt文件
        InputStream in = DeptDaoFileImpl.class.getClassLoader().getResourceAsStream("depts.txt");

        List<String> lines = IOUtils.readLines(in, "UTF-8");
        return lines;
    }
}
