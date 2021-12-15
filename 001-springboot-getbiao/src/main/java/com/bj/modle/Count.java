package com.bj.modle;

import basehao.BaseHao;

public class Count extends BaseHao {
    private String tableName;   //表名
    private String tableZS;     //表的注释
    private String jianbiao;    //建表语句

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }



    public String getTableZS() {
        return tableZS;
    }

    public void setTableZS(String tableZS) {
        this.tableZS = tableZS;
    }

    public String getJianbiao() {
        return jianbiao;
    }

    public void setJianbiao(String jianbiao) {
        this.jianbiao = jianbiao;
    }
}
