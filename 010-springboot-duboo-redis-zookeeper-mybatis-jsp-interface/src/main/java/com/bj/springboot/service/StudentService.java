package com.bj.springboot.service;

import com.bj.springboot.model.Student;

public interface StudentService {
    Integer queryStudentAllCount();

    Student queryStudentDetailById(Integer id);
}
