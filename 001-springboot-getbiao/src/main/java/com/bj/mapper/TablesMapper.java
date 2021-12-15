package com.bj.mapper;

import com.bj.modle.Count;
import com.bj.modle.Tables;
import com.bj.modle.TablesColumns;

import java.util.List;
import java.util.Map;

public interface TablesMapper {
    List<Tables> getFindTableNameAndCount();

    Integer getFindTableSumCount(Count count);

    Map<String,Object> getTablesName(Count count);

    Integer getFindTableSumSJDCount(Count count);

    List<TablesColumns> getTablescolumnsComment(Count count);
}
