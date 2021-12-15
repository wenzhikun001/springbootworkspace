package com.bj.springboot.web;

import com.bj.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    @RequestMapping(value = "studentCount")
    public @ResponseBody Object studentCount(){
        Integer count  =studentService.findAllStudentCount();
        return count;
    }
}
