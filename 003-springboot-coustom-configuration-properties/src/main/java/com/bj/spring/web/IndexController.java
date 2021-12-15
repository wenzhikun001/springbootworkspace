package com.bj.spring.web;

import com.bj.spring.model.City;
import com.bj.spring.model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired //因为School已放到容器中，直接注入
    private School school;

    @Autowired
    private City city; //因为City已放到容器中，直接注入

    @RequestMapping(value = "school")
    public @ResponseBody
    Object school() {
        return "school.name:" + school.getName() + ",school.website:" + school.getWebsite();
    }

    @RequestMapping(value = "city")
    public @ResponseBody
    Object city() {
        return "city.name:" + city.getName() + "city.website:" + city.getWebsite();
    }
}
