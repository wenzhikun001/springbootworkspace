package com.bj.springboot.dubbo.web;
import com.alibaba.dubbo.config.annotation.Reference;
import com.bj.springboot.dubbo.service.SomeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SomeController {
    //@Reference相当于dubbo:reference interface="" version="" check=false
    @Reference(interfaceClass = SomeService.class,version = "1.0.0",check = false)
    private SomeService somService;
    @RequestMapping(value = "/hello")
    public @ResponseBody Object hello(){
        //调用远程接口服务
        String hello=somService.hello();
        return hello;
    }
}