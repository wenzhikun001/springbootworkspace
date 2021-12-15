package com.bj.service;


import com.bj.modle.Count;
import com.bj.modle.Tables;

import java.util.HashMap;
import java.util.List;

public interface TablesService {

    List<Tables> getFindTableNameAndCount();

    HashMap<String,Object> getFindTableSumCount(List<Tables> list) ;

    String writeDetail(List<Count> list, String path);

    HashMap<String, Object> getfindTbaleSumSJDCount(List<Tables> list, String startTime, String endTime);
}
