package com.bj.springboot.web;

import com.bj.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class StudentController1 {
    @Autowired
    private StudentService studentService;
    @RequestMapping(value = "studentCount1")
    public @ResponseBody Object studentCount(){
        //定义一个固定的线程池
        /**
         * 输出的则为：
         * 从数据库中获取
         * 从数据库中获取
         * 从数据库中获取
         * 从redis中获取
         * 从redis中获取
         * 存在缓存穿透的现象
         */
        ExecutorService executorService=Executors.newFixedThreadPool(100);//最多容纳100个同时访问
        //模拟多线程访问
        for (int i = 0; i < 1000; i++) {
            //开启一个线程
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Integer count  =studentService.findAllStudentCount();
                }
            });
        }
        //executorService.shutdown();
        Integer count  =studentService.findAllStudentCount();
        return count;
    }
}
