package serviceImpl.gaj;

import bean.BaoAnBean;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BaoAnImpl {
    public static void main(String[] args) {
        File file=new File("C:\\test1\\ga\\保安员信息.xlsx");
        List<BaoAnBean>list= redShuoJu(file);
        //获取数据库中的数据源
        try {
            File filexl=new File("C:\\test1\\ga\\shujukudistis.txt");
            InputStreamReader readersl=new InputStreamReader(new FileInputStream(filexl),"UTF-8");
            BufferedReader bufferedReaderxl=new BufferedReader(readersl);
            String lineTxtxl=null;
            List listMysql=new ArrayList();
            while ((lineTxtxl=bufferedReaderxl.readLine())!=null){
                listMysql.add(lineTxtxl);
            }
            listMysql.stream().distinct().collect(Collectors.toList());
            List<BaoAnBean> lists=new ArrayList<>();
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
            String path="C:\\test1\\ga\\库中有数据但未更新的数据表.xlsx";
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
    private static List<BaoAnBean> redShuoJu(File file){
        List<BaoAnBean> list=new ArrayList<BaoAnBean>();
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
                BaoAnBean baoAnBean=new BaoAnBean();
                baoAnBean.setXm(row.getCell(0).getStringCellValue());
                baoAnBean.setXb(row.getCell(1).getStringCellValue());
                baoAnBean.setMz(row.getCell(2).getStringCellValue());
                baoAnBean.setSfzhm(row.getCell(3).getStringCellValue());
                String babh=getCellValueByCell(row.getCell(4));
                baoAnBean.setBazbh(babh);
                baoAnBean.setSsxzq(row.getCell(5).getStringCellValue());
                baoAnBean.setHjdqy(row.getCell(6).getStringCellValue());
                baoAnBean.setFwdw(row.getCell(7).getStringCellValue());
                /*if (faRenBean.getQylx().equals("私营")){*/
                list.add(baoAnBean);
                //}
            }
        }catch (IOException | InvalidFormatException e){
            e.printStackTrace();
        }
        /*System.out.println("list大小为:"+list.size());
        System.out.println(JSONObject.toJSONString(list));*/
        return list;
    }

    public static String writeDetail(List<BaoAnBean> list2, String path) {


        String message="写入成功";
        //创建一个工作蒲
        XSSFWorkbook xssfWorkbook =new XSSFWorkbook();
        //创建工作表
        XSSFSheet xssfSheet=xssfWorkbook.createSheet("sheet0");
        //创建行
        XSSFRow row=xssfSheet.createRow(0);
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("性别");
        row.createCell(2).setCellValue("民族");
        row.createCell(3).setCellValue("身份证号码");
        row.createCell(4).setCellValue("保安证编号");
        row.createCell(5).setCellValue("所属行政区");
        row.createCell(6).setCellValue("户籍地区域");
        row.createCell(7).setCellValue("服务单位");
        for (int i = 0; i < list2.size(); i++) {
            XSSFRow row2=xssfSheet.createRow(i+1);
            row2.createCell(0).setCellValue(list2.get(i).getXm());
            row2.createCell(1).setCellValue(list2.get(i).getXb());
            row2.createCell(2).setCellValue(list2.get(i).getMz());
            row2.createCell(3).setCellValue(list2.get(i).getSfzhm());
            row2.createCell(4).setCellValue(list2.get(i).getBazbh());
            row2.createCell(5).setCellValue(list2.get(i).getSsxzq());
            row2.createCell(6).setCellValue(list2.get(i).getHjdqy());
            row2.createCell(7).setCellValue(list2.get(i).getFwdw());
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
    //获取单元格各类型值，返回字符串类型
    public static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType=cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                short format = cell.getCellStyle().getDataFormat();
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    //System.out.println("cell.getCellStyle().getDataFormat()="+cell.getCellStyle().getDataFormat());
                    if (format == 20 || format == 32) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else if (format == 14 || format == 31 || format == 57 || format == 58) {
                        // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        double value = cell.getNumericCellValue();
                        Date date = org.apache.poi.ss.usermodel.DateUtil
                                .getJavaDate(value);
                        cellValue = sdf.format(date);
                    }else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    try {
                        cellValue = sdf.format(cell.getDateCellValue());// 日期
                    } catch (Exception e) {
                        try {
                            throw new Exception("exception on get date data !".concat(e.toString()));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }finally{
                        sdf = null;
                    }
                }  else {
                    BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                    cellValue = bd.toPlainString();// 数值 这种用BigDecimal包装再获取plainString，可以防止获取到科学计数值
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue()+"";;
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "ERROR VALUE";
                break;
            default:
                cellValue = "UNKNOW VALUE";
                break;
        }
        return cellValue;
    }
}
