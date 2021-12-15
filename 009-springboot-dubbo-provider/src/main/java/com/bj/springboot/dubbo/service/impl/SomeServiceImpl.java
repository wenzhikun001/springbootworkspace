package com.bj.springboot.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.bj.springboot.dubbo.service.SomeService;
import org.springframework.stereotype.Component;

@Component //交给spring容器管理
//@Service 相当于dubbo:service nterfaceClass = "",ref="" version = "",timeout = ""
@Service(interfaceClass = SomeService.class,version = "1.0.0",timeout = 35000)
public class SomeServiceImpl implements SomeService {
    @Override
    public String hello() {
         return "hello SpeingBoot集成dubbo";
    }
}
