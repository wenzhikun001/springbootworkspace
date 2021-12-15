package com.bj.springboot.service;

import com.alibaba.fastjson.JSONObject;
import com.bj.springboot.mapper.StudentMapper;
import com.bj.springboot.model.Student;
import com.bj.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Transactional
    @Override
    public int updateStudentStudentNameById(Student student) {
        int count=studentMapper.updateByPrimaryKeySelective(student);
        System.out.println("更新了一条数据:"+ JSONObject.toJSONString(student));
        int a=10/0;
        return count;
    }

    @Override
    public Student getStudentById(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }
}
