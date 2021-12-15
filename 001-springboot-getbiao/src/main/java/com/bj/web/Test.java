package com.bj.web;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        String a="[{I_ID=自增主键}, {xm=无}, {dz=无}, {S_CREATETIME=S_CREATETIME}, {S_LAST_UPDATETIME=S_LAST_UPDATETIME}]";
        HashMap<String,String> map=new HashMap<>();
        map.put("I_ID","自增主键");
        map.put("xm","无");
        List list1=new ArrayList();
        list1.add(map);
        list1= (List) JSONObject.toJSON(list1);
        System.out.println(list1.toString());
    }
}
