package com.bj.springboot.web;

import com.bj.springboot.model.Student;
import com.bj.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/update")
    public @ResponseBody Object updateById(Integer id, String studentName){
        Student student=new Student();
        student.setId(id);
        student.setUserName(studentName);
        int count =studentService.updateStudentStudentNameById(student);
        return count;
    }

    @RequestMapping(value = "/detail")
    public @ResponseBody Object selectById(Integer id){
        Student student=studentService.getStudentById(id);
        return student;
    }
}
