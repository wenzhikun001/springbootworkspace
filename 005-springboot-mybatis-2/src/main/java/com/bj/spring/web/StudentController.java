package com.bj.spring.web;

import com.bj.spring.model.Student;
import com.bj.spring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    @RequestMapping(value = "/student/detail")
    public @ResponseBody Object StudentDetail(Integer id){
        Student student=studentService.getStudentById(id);
        return student;
    }
}
