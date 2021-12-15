package com.bj.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {
    @RequestMapping(value = "/index")
    public @ResponseBody Object hello(){
        return "hello";
    }
}
