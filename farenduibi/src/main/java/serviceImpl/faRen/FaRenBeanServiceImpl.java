package serviceImpl.faRen;

import bean.FaRenBean;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FaRenBeanServiceImpl {
    public static void main(String[] args) {
        File file=new File("C:\\test1\\fr\\suzhoushifarenxxx.xlsx");
        List<FaRenBean>list= redShuoJu(file);
        //获取数据库中的数据源
        try {
            File filexl=new File("C:\\test1\\fr\\shuojuku.txt");
            InputStreamReader readersl=new InputStreamReader(new FileInputStream(filexl),"UTF-8");
            BufferedReader bufferedReaderxl=new BufferedReader(readersl);
            String lineTxtxl=null;
            List listMysql=new ArrayList();
            while ((lineTxtxl=bufferedReaderxl.readLine())!=null){
                listMysql.add(lineTxtxl);
            }
            listMysql.stream().distinct().collect(Collectors.toList());
            List<FaRenBean> lists=new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (listMysql.contains(list.get(i).getZch())){
                    continue;
                }else {
                    lists.add(list.get(i));
                }
            }

            System.out.println("lists对比后的大小为:"+lists.size());
            System.out.println(JSONObject.toJSONString(lists));
            //将此对比的结果写入到工作薄中
            String path="C:\\test1\\fr\\库中未查询到的数据表.xlsx";
            writeDetail(lists,path);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //把表格读取到了list中
    private static List<FaRenBean> redShuoJu(File file){
        List<FaRenBean> list=new ArrayList<FaRenBean>();
        try {
            //获取文件输入流
            InputStream inputStream=new FileInputStream(file);
            //HSSFWorkbook和XSSFWorkbook都实现了Workbook接口，采用work接收
            Workbook workbook= WorkbookFactory.create(inputStream);
            //得到excel工作表对象
            Sheet sheetAt=workbook.getSheetAt(0);
            //循环读取表数据
            for (Row row:sheetAt){
                //首行不读取
                if (row.getRowNum()==0||row.getRowNum()==1){
                    continue;
                }
                //读取当前行中单元数据，索引从0开始
                Cell cell=row.getCell(0);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                FaRenBean faRenBean=new FaRenBean();
                faRenBean.setQymc(row.getCell(0).getStringCellValue());
                faRenBean.setShxydm(row.getCell(1).getStringCellValue());
                faRenBean.setDjjg(row.getCell(2).getStringCellValue());
                faRenBean.setQylx(row.getCell(3).getStringCellValue());
                faRenBean.setZch(row.getCell(4).getStringCellValue());
                faRenBean.setZz(row.getCell(5).getStringCellValue());
                faRenBean.setClrq(row.getCell(6).getStringCellValue());
                faRenBean.setFddbr(row.getCell(7).getStringCellValue());
                /*if (faRenBean.getQylx().equals("私营")){*/
                    list.add(faRenBean);
                //}
            }
        }catch (IOException | InvalidFormatException e){
            e.printStackTrace();
        }
        /*System.out.println("list大小为:"+list.size());
        System.out.println(JSONObject.toJSONString(list));*/
        return list;
    }

    public static String writeDetail(List<FaRenBean> list2, String path) {


        String message="写入成功";
        //创建一个工作蒲
        XSSFWorkbook xssfWorkbook =new XSSFWorkbook();
        //创建工作表
        XSSFSheet xssfSheet=xssfWorkbook.createSheet("sheet0");
        //创建行
        XSSFRow row=xssfSheet.createRow(0);
        row.createCell(0).setCellValue("企业名称");
        row.createCell(1).setCellValue("社会信用代码");
        row.createCell(2).setCellValue("登记机关");
        row.createCell(3).setCellValue("企业类型");
        row.createCell(4).setCellValue("注册号");
        row.createCell(5).setCellValue("地址");
        row.createCell(6).setCellValue("成立日期");
        row.createCell(7).setCellValue("法定代表人");
        for (int i = 0; i < list2.size(); i++) {
            XSSFRow row2=xssfSheet.createRow(i+1);
            row2.createCell(0).setCellValue(list2.get(i).getQymc());
            row2.createCell(1).setCellValue(list2.get(i).getShxydm());
            row2.createCell(2).setCellValue(list2.get(i).getDjjg());
            row2.createCell(3).setCellValue(list2.get(i).getQylx());
            row2.createCell(4).setCellValue(list2.get(i).getZch());
            row2.createCell(5).setCellValue(list2.get(i).getZz());
            row2.createCell(6).setCellValue(list2.get(i).getClrq());
            row2.createCell(7).setCellValue(list2.get(i).getFddbr());
        }
        try {
           /* File file = new File(path);
            if (!file.exists()) {
                // 先得到文件的上级目录，并创建上级目录，在创建文件
                file.getParentFile().mkdir();
                file.createNewFile();
            }*/
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
}
