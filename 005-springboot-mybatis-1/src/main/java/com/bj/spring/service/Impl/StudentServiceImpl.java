package com.bj.spring.service.Impl;

import com.bj.spring.mapper.StudentMapper;
import com.bj.spring.model.Student;
import com.bj.spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired(required=false)
    private StudentMapper studentMapper;

    @Override
    public Student getStudentById(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }
}
