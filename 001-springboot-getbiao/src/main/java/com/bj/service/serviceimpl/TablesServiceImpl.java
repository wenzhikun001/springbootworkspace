package com.bj.service.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.bj.mapper.TablesMapper;
import com.bj.modle.Count;
import com.bj.modle.Tables;
import com.bj.modle.TablesColumns;
import com.bj.service.TablesService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TablesServiceImpl implements TablesService {
    Integer countmysqlTableSum=0;
    //注入mapper层
    @Autowired
    private TablesMapper tablesMapper;

    @Autowired  //注入redis模板
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public List<Tables> getFindTableNameAndCount() {
        List<Tables> list=tablesMapper.getFindTableNameAndCount();
        List<Tables>list1=new ArrayList<>();
        List listMysql=new ArrayList();
        try {
            File filexl=new File("C:\\test1\\dangsansysquchubiao.txt");
            InputStreamReader readersl=new InputStreamReader(new FileInputStream(filexl),"UTF-8");
            BufferedReader bufferedReaderxl=new BufferedReader(readersl);
            String lineTxtxl=null;

            while ((lineTxtxl=bufferedReaderxl.readLine())!=null){
                listMysql.add(lineTxtxl);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            if (isChinese(list.get(i).getTable_name().charAt(0))||"jyj_czqcjyxk-clyyxk-jsykyzgxk".equals(list.get(i).getTable_name())
                    ||"$".equals(list.get(i).getTable_name().charAt(0))
            ||"$".equals(list.get(i).getTable_name().charAt(1))
            ||(listMysql.contains(list.get(i).getTable_name()))){
                continue;
            }else {
                list1.add(list.get(i));
            }
        }
        return list1;
    }

    //判断名字是否是汉字
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    @Override
    public HashMap<String, Object> getFindTableSumCount(List<Tables> list) {
        //过呢更新redisTemlate模板中的序列化 否则redis中的名称是这样的\xAC\xED\x00\x05t\x00\x0FallStudentCount,设置之后是这样的 allStudentCount
       /* redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set("chengguanCount1",2074992);*/
       /* //先去redis中查询一下是否有数据
        Integer countmysqlTables= (Integer) redisTemplate.opsForValue().get("chengguanCount1");*/

        HashMap<String,Object> map1=new HashMap<>();
        List<Count>listCount=new ArrayList<>();
        try {
            for (int i = 0; i < list.size(); i++) {
                List<HashMap<String, String>> list1=new ArrayList();
                Count count=new Count();
                String tableName=list.get(i).getTable_schema()+"."+list.get(i).getTable_name();
                count.setTableName(tableName);
                Map<String,Object> map=tablesMapper.getTablesName(count);
                String  tablesNameZhuShi=map.values().toString();
                //获取具体表的字段
                List<TablesColumns> tablesColumns=tablesMapper.getTablescolumnsComment(count);
                for (int j = 0; j < tablesColumns.size(); j++) {
                    HashMap<String,String> hashMap=new HashMap<>();
                    if ("".equals(tablesColumns.get(j).getComment())){
                        hashMap.put(tablesColumns.get(j).getField(),"数据库中改字段无注释");
                    }else {
                        hashMap.put(tablesColumns.get(j).getField(),tablesColumns.get(j).getComment());
                    }
                    list1.add(hashMap);
                }

                /*for (int j = 0; j < tablesColumns.size(); j++) {
                    list1.add(tablesColumns.get(i).getComment());
                }*/
                //System.out.println(JSONObject.toJSONString(list1));
                tablesNameZhuShi=parse(tablesNameZhuShi);
                list1= (List) JSONObject.toJSON(list1);
                count.setJianbiao(list1.toString());
                count.setTableZS(tablesNameZhuShi);
                count.setTableName(list.get(i).getTable_name());
                listCount.add(count);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        map1.put("list",listCount);
       map1.put("总数据量为:",(countmysqlTableSum));
        countmysqlTableSum=0;
        return map1;
    }

    /**
     * 创建工作蒲，然后将数据导出到excel表格中
     * @param list2
     * @param path
     */
    @Override
    public String writeDetail(List<Count> list2, String path) {


        String message="写入成功";
        //创建一个工作蒲
        XSSFWorkbook xssfWorkbook =new XSSFWorkbook();
        //创建工作表
        XSSFSheet xssfSheet=xssfWorkbook.createSheet("数据总和明细");
        //创建行
        XSSFRow row=xssfSheet.createRow(0);
        row.createCell(0).setCellValue("表名");
        row.createCell(1).setCellValue("表注释");
        row.createCell(2).setCellValue("建表语句");
        XSSFRow row1=xssfSheet.createRow(1);
        row1.createCell(0).setCellValue("tableName");
        row1.createCell(1).setCellValue("tableZS");
        row1.createCell(2).setCellValue("jianbiao");
        for (int i = 0; i < list2.size(); i++) {
            XSSFRow row2=xssfSheet.createRow(i+2);
            row2.createCell(0).setCellValue(list2.get(i).getTableName());
            row2.createCell(1).setCellValue(list2.get(i).getTableZS());
            row2.createCell(2).setCellValue(list2.get(i).getJianbiao());
        }
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(path);
            xssfWorkbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            message="写入失败";
        } catch (IOException e) {
            e.printStackTrace();
            message="写入失败";
        }
        return message;
    }

    @Override
    public HashMap<String, Object> getfindTbaleSumSJDCount(List<Tables> list, String startTime, String endTime) {
        HashMap<String,Object> map1=new HashMap<>();
        List list1=new ArrayList();
        List<Count>listCount=new ArrayList<>();
        try {
            for (int i = 0; i < list.size(); i++) {
                Count count=new Count();
                String tableName=list.get(i).getTable_schema()+"."+list.get(i).getTable_name();
                count.setTableName(tableName);
                Map<String,Object> map=tablesMapper.getTablesName(count);
                String  tablesNameZhuShi=map.values().toString();
                tablesNameZhuShi=parse(tablesNameZhuShi);
                if ("无".equals(tablesNameZhuShi)){
                    list1.add(tableName);
                }
                count.setTableZS(tablesNameZhuShi);
                count.setCreatime(startTime);
                count.setUpdateTime(endTime);
                System.out.println("去数据库查询的条件为:"+ JSONObject.toJSONString(count));
                Integer countmysql =tablesMapper.getFindTableSumSJDCount(count);
                if (0==countmysql)
                    continue;
                count.setCount(countmysql);
                System.out.println("查询出来的数据为:"+ JSONObject.toJSONString(count));
                countmysqlTableSum+=countmysql;
                listCount.add(count);
            }
            System.out.println("注释为空的数据库为:"+JSONObject.toJSONString(list1));
        }catch (Exception e) {
            e.printStackTrace();
        }
        map1.put("list",listCount);
        map1.put("表的总数为:",countmysqlTableSum);
        countmysqlTableSum=0;
        return map1;
    }

    public  String parse(String all) {
        String comment= null;

        int index = all.indexOf("COMMENT='");//获取字符串所在的下标
        if (index < 0) { //若是不存再则返回null
            return "";
        }
        /*comment= all.substring(index + 15);
        comment= comment.substring(0, comment.length() - 1);*/
        //COMMENT='房产-新建商品房预售许可证'这个字符串中进行截取，去除COMMENT='这个字符串的长度，也就是加9，结束为总字符串的长度-1
        comment= all.substring(index + 9,all.length()-2);
        return comment;
    }
    public static void main(String[] args) {
        String all="[t_fc_qt_xjspfysxkz, CREATE TABLE `t_fc_qt_xjspfysxkz` (\n" +
                "  `l_ysxk_incode` decimal(38,0) NOT NULL,\n" +
                "  `l_xm_incode` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_kfs_incode` decimal(38,0) DEFAULT NULL,\n" +
                "  `s_ysbh` varchar(50) DEFAULT NULL,\n" +
                "  `s_djh` varchar(50) DEFAULT NULL,\n" +
                "  `s_jgyh` varchar(50) DEFAULT NULL,\n" +
                "  `s_yhzh` varchar(50) DEFAULT NULL,\n" +
                "  `l_zts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_fszzts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_sq_ts` decimal(38,0) DEFAULT NULL,\n" +
                "  `s_ysdx` varchar(50) DEFAULT NULL,\n" +
                "  `dc_ysjzmj` float(8,0) DEFAULT NULL,\n" +
                "  `l_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jnzz_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jwzz_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jnbs_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jwbs_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jnbg_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jwbg_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jnsy_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jwsy_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jnqt_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_jwqt_ysts` decimal(38,0) DEFAULT NULL,\n" +
                "  `dt_shenqing` timestamp NULL DEFAULT NULL,\n" +
                "  `dt_tianfa` timestamp NULL DEFAULT NULL,\n" +
                "  `s_beizhu` varchar(500) DEFAULT NULL,\n" +
                "  `i_shenhe` decimal(38,0) DEFAULT NULL,\n" +
                "  `s_dong_bh` varchar(50) DEFAULT NULL,\n" +
                "  `i_updated` decimal(38,0) DEFAULT NULL,\n" +
                "  `l_anzhits` decimal(38,0) DEFAULT NULL,\n" +
                "  `s_fwyt1` varchar(50) DEFAULT NULL,\n" +
                "  `s_fwyt2` varchar(50) DEFAULT NULL,\n" +
                "  `s_fwyt3` varchar(50) DEFAULT NULL,\n" +
                "  `s_fwyt4` varchar(50) DEFAULT NULL,\n" +
                "  `s_fwyt5` varchar(50) DEFAULT NULL,\n" +
                "  `iflytek_jhsj` varchar(14) DEFAULT NULL COMMENT '交换时间',\n" +
                "  `id` varchar(50) DEFAULT NULL COMMENT '主键id',\n" +
                "  `jh_create_time` timestamp NULL DEFAULT NULL COMMENT '入交换平台前置机交换时间',\n" +
                "  `qz_create_time` timestamp NULL DEFAULT NULL COMMENT '入前置库时间',\n" +
                "  `xyptbatchguid` varchar(36) DEFAULT NULL COMMENT '批次号',\n" +
                "  `xyptbatchno` int(8) DEFAULT NULL COMMENT '时间批次',\n" +
                "  `xyptguid` varchar(36) DEFAULT NULL COMMENT '唯一标识',\n" +
                "  `change_flag` varchar(1) DEFAULT NULL COMMENT '变更标识 1：新增 2：修改 3：删除',\n" +
                "  `dc_zjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jnzz_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jwzz_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jnbs_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jwbs_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jnbg_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jwbg_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jnsy_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jwsy_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jnqt_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_jwqt_ysjzmj` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_nsj_zz` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_nsj_bs` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_nsj_sy` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_nsj_bg` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_nsj_qt` decimal(20,2) DEFAULT NULL,\n" +
                "  `dc_anzhimj` decimal(20,2) DEFAULT NULL,\n" +
                "  `primeton_jhsj` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间'\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='房产-新建商品房预售许可证']";

        String comment= null;
        int index = all.indexOf("COMMENT='");
        System.out.println("index====:"+index);
        comment= all.substring(index + 9,all.length()-2);
        System.out.println("comment:"+comment);
    }
}
