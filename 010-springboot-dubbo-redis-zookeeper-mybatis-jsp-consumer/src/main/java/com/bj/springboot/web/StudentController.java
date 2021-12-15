package com.bj.springboot.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bj.springboot.model.Student;
import com.bj.springboot.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudentController {
    @Reference(interfaceClass = StudentService.class,version = "1.0.0",check = false)
    private StudentService studentService;

    @RequestMapping(value = "/student/count")
    public  String count(Model model){
        Integer allStudentCount=studentService.queryStudentAllCount();
        model.addAttribute("allStudentCount",allStudentCount);
        return "allStudentCount";
    }
    @RequestMapping(value = "/student/detail/{id}")
    public String studentDetail(Model model, @PathVariable("id") Integer id){
        Student student=studentService.queryStudentDetailById(id);
        model.addAttribute("studentDetail",student);
        return "studentDetail";
    }
}
