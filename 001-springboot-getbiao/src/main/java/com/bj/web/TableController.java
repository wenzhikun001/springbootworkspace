package com.bj.web;


import com.bj.modle.Count;
import com.bj.modle.Tables;
import com.bj.service.TablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class TableController {
    HashMap<String,Object> map=new HashMap<>();
    List<Tables>list=new ArrayList<>();
    @Autowired
    private TablesService tablesService;
    //用户输入的为查询所有表名和总表数
    @RequestMapping(value = "findTableNameAndCount")
    public @ResponseBody Object findTableNameAndCount(){
        list=tablesService.getFindTableNameAndCount();
        map.put("list",list);
        map.put("数据表的总数为:",list.size());
        return map;
    }
    @RequestMapping(value = "findTableSumCount")
    public @ResponseBody Object findTableSumCount(){
        map=tablesService.getFindTableSumCount((List) map.get("list"));
        return map;
    }
    @RequestMapping(value = "findTbaleSumSJDCount")
    public @ResponseBody Object findTbaleSumSJDCount(String startTime,String endTime){
        map=tablesService.getfindTbaleSumSJDCount((List) map.get("list"),startTime,endTime);
        return map;
    }
    @RequestMapping(value = "writeDetail")
    public @ResponseBody Object write(){
        String path="C:\\test1\\aa.xlsx";
        String message =tablesService.writeDetail((List<Count>) map.get("list"),path);
        return message;
    }
}
