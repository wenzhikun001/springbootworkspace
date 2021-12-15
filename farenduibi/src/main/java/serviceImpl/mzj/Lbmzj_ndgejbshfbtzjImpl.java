package serviceImpl.mzj;

import bean.Lbmzj_ndgejbshfbtzjBean;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.CellValueZhuanHuan;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lbmzj_ndgejbshfbtzjImpl {
    public static void main(String[] args) {
        File file=new File("C:\\test1\\minzhengju\\年度孤儿基本生活费补贴资金（社会事务股2021年11月）(1).xls");
        List<Lbmzj_ndgejbshfbtzjBean>list= redShuoJu(file);
        //获取数据库中的数据源
        try {
            File filexl=new File("C:\\test1\\minzhengju\\shujuuku.txt");
            InputStreamReader readersl=new InputStreamReader(new FileInputStream(filexl),"UTF-8");
            BufferedReader bufferedReaderxl=new BufferedReader(readersl);
            String lineTxtxl=null;
            List listMysql=new ArrayList();
            while ((lineTxtxl=bufferedReaderxl.readLine())!=null){
                listMysql.add(lineTxtxl);
            }
            listMysql.stream().distinct().collect(Collectors.toList());
            List<Lbmzj_ndgejbshfbtzjBean> lists=new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (listMysql.contains(list.get(i).getSfzhm())){
                    continue;
                }else {
                    lists.add(list.get(i));
                }
            }
            System.out.println("lists对比后的大小为:"+lists.size());
            System.out.println(JSONObject.toJSONString(lists));
            //将此对比的结果写入到工作薄中
            String path="C:\\test1\\minzhengju\\库中没有的数据表.xlsx";
            System.out.println(writeDetail(lists,path));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //把表格读取到了list中
    private static List<Lbmzj_ndgejbshfbtzjBean> redShuoJu(File file){
        List<Lbmzj_ndgejbshfbtzjBean> list=new ArrayList<Lbmzj_ndgejbshfbtzjBean>();
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
                Lbmzj_ndgejbshfbtzjBean lbmzj_ndgejbshfbtzjBean=new Lbmzj_ndgejbshfbtzjBean();
                lbmzj_ndgejbshfbtzjBean.setNd(row.getCell(0).getStringCellValue());
                lbmzj_ndgejbshfbtzjBean.setXsr(row.getCell(1).getStringCellValue());
                lbmzj_ndgejbshfbtzjBean.setXb(row.getCell(2).getStringCellValue());
                lbmzj_ndgejbshfbtzjBean.setSfzhm(row.getCell(3).getStringCellValue());
                lbmzj_ndgejbshfbtzjBean.setJtzz(row.getCell(4).getStringCellValue());
                String bzje= CellValueZhuanHuan.getCellValueByCell(row.getCell(5));
                lbmzj_ndgejbshfbtzjBean.setBzje(bzje);
                lbmzj_ndgejbshfbtzjBean.setZjhm(row.getCell(6).getStringCellValue());
                String hzbm=CellValueZhuanHuan.getCellValueByCell(row.getCell(7));
                lbmzj_ndgejbshfbtzjBean.setHzbm(hzbm);
                String hzxm=CellValueZhuanHuan.getCellValueByCell(row.getCell(8));
                lbmzj_ndgejbshfbtzjBean.setHzxm(hzxm);
                lbmzj_ndgejbshfbtzjBean.setYktczzh(row.getCell(9).getStringCellValue());
                String lxdh=CellValueZhuanHuan.getCellValueByCell(row.getCell(10));
                lbmzj_ndgejbshfbtzjBean.setLxdh(lxdh);
                lbmzj_ndgejbshfbtzjBean.setBz(row.getCell(11).getStringCellValue());

                /*if (faRenBean.getQylx().equals("私营")){*/
                list.add(lbmzj_ndgejbshfbtzjBean);
                //}
            }
        }catch (IOException | InvalidFormatException e){
            e.printStackTrace();
        }
        /*System.out.println("list大小为:"+list.size());
        System.out.println(JSONObject.toJSONString(list));*/
        return list;
    }

    public static String writeDetail(List<Lbmzj_ndgejbshfbtzjBean> list2, String path) {


        String message="写入成功";
        //创建一个工作蒲
        XSSFWorkbook xssfWorkbook =new XSSFWorkbook();
        //创建工作表
        XSSFSheet xssfSheet=xssfWorkbook.createSheet("sheet0");
        //创建行
        XSSFRow row=xssfSheet.createRow(0);
        row.createCell(0).setCellValue("nd");
        row.createCell(1).setCellValue("xsr");
        row.createCell(2).setCellValue("xb");
        row.createCell(3).setCellValue("sfzhm");
        row.createCell(4).setCellValue("jtzz");
        row.createCell(5).setCellValue("bzje");
        row.createCell(6).setCellValue("zjhm");
        row.createCell(7).setCellValue("hzbm");
        row.createCell(8).setCellValue("hzxm");
        row.createCell(9).setCellValue("yktczzh");
        row.createCell(10).setCellValue("lxdh");
        row.createCell(11).setCellValue("bz");
        for (int i = 0; i < list2.size(); i++) {
            XSSFRow row2=xssfSheet.createRow(i+1);
            row2.createCell(0).setCellValue(list2.get(i).getNd());
            row2.createCell(1).setCellValue(list2.get(i).getXsr());
            row2.createCell(2).setCellValue(list2.get(i).getXb());
            row2.createCell(3).setCellValue(list2.get(i).getSfzhm());
            row2.createCell(4).setCellValue(list2.get(i).getJtzz());
            row2.createCell(5).setCellValue(list2.get(i).getBzje());
            row2.createCell(6).setCellValue(list2.get(i).getZjhm());
            row2.createCell(7).setCellValue(list2.get(i).getHzbm());
            row2.createCell(8).setCellValue(list2.get(i).getHzxm());
            row2.createCell(9).setCellValue(list2.get(i).getYktczzh());
            row2.createCell(10).setCellValue(list2.get(i).getLxdh());
            row2.createCell(11).setCellValue(list2.get(i).getBz());
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
