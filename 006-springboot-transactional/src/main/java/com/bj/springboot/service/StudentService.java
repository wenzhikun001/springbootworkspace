package com.bj.springboot.service;

import com.bj.springboot.model.Student;

public interface StudentService {
    int updateStudentStudentNameById(Student student);

    Student getStudentById(Integer id);
}
