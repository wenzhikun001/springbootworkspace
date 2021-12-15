package com.bj.springboot.web;

import org.springframework.web.bind.annotation.*;

 //@Controller和@ResponseBody的组合注解
//如果一个Controller类添加了RestController，那么Controller类下的所有方法都相当于添加了@ResponseBody注解
//用于返回字符串或json数据
 @RestController
public class IndexController {
    //该方法即支持get，也支持post,支持所有的请求
    @RequestMapping(value = "/index")
    //@ResponseBody
    public  Object index(){
        return "index";
    }
    //该方法仅支持get，相当于@GetMapping
    @RequestMapping(value = "/index1",method = RequestMethod.GET)
    //@ResponseBody
    public  Object index1(){
        return "index1 仅支持get请求";
    }
    @GetMapping(value = "index2")
    //@ResponseBody
    public  Object index2(){
        return "index2 GetMapping 仅支持get请求";
    }
    //该方法仅支持Post，相当于@PostMapping
    @RequestMapping(value = "/index3",method = RequestMethod.POST)
    //@ResponseBody
    public  Object index3(){
        return "index3 仅支持post请求";
    }
    @PostMapping(value = "index4")
    //@ResponseBody
    public  Object index4(){
        return "index4 PostMapping 仅支持post请求";
    }
    @PutMapping(value = "index5")
    //@ResponseBody
    public  Object index5(){
        return "index5 putMapping 仅支持修改请求";
    }
    @DeleteMapping(value = "index6")
    //@ResponseBody
    public  Object index6(){
        return "index6 DeleteMapping 仅支持删除请求";
    }
}
