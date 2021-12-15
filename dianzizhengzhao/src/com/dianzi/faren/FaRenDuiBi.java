/*
package com.dianzi.faren;

import com.dianzi.bean.FaRenBean;
import com.sun.media.sound.InvalidFormatException;
import com.sun.rowset.internal.Row;
import javafx.scene.control.Cell;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FaRenDuiBi {
    public static void main(String[] args) {
        try {
            File filesl=new File("C:\\test1\\fr\\sshilimiandeshuju.txt");
            File filexl=new File("C:\\test1\\fr\\shuojuku.txt");
            InputStreamReader readersl=new InputStreamReader(new FileInputStream(filesl),"UTF-8");
            BufferedReader bufferedReadersl=new BufferedReader(readersl);
            InputStreamReader readerxl=new InputStreamReader(new FileInputStream(filexl),"UTF-8");
            BufferedReader bufferedReaderxl=new BufferedReader(readerxl);
            String lineTxtsl=null;
            String lineTxtxl=null;
            List listSL=new ArrayList();
            List listMysql=new ArrayList();
            while ((lineTxtsl=bufferedReadersl.readLine())!=null){
                listSL.add(lineTxtsl);
            }
            while ((lineTxtxl=bufferedReaderxl.readLine())!=null){
                listMysql.add(lineTxtxl);
            }
            List duibi=new ArrayList();
            for (int i = 0; i < listSL.size(); i++) {
                if (listMysql.contains(listSL.get(i))){
                    continue;
                }else {
                    duibi.add(listSL.get(i));
                }
            }


            System.out.println(listSL);
            System.out.println(listMysql);
            System.out.println(duibi.stream().distinct().collect(Collectors.toList()));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<FaRenBean> redShuoJu(File file) throws IOException, InvalidFormatException {
        List<FaRenBean> list=new ArrayList<FaRenBean>();
        //获取文件输入流
        InputStream inputStream=new FileInputStream(file);
        //HSSFWorkbook和XSSFWorkbook都实现了Workbook接口，采用work接收
        Workbook workbook=WorkbookFactory.create(inputStream);
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
            Lbmzj_yljgslxkzEntity lbmzj_yljgslxkzEntity=new Lbmzj_yljgslxkzEntity();
            lbmzj_yljgslxkzEntity.setDwmc(row.getCell(0).getStringCellValue());
            lbmzj_yljgslxkzEntity.setFddbr(row.getCell(1).getStringCellValue());
            lbmzj_yljgslxkzEntity.setTyshxydmzsbh(row.getCell(2).getStringCellValue());
            lbmzj_yljgslxkzEntity.setZs(row.getCell(3).getStringCellValue());
            lbmzj_yljgslxkzEntity.setFwfw(row.getCell(4).getStringCellValue());
            lbmzj_yljgslxkzEntity.setYxqq(row.getCell(5).getStringCellValue());
            lbmzj_yljgslxkzEntity.setYxqz(row.getCell(6).getStringCellValue());
            lbmzj_yljgslxkzEntity.setFzjg(row.getCell(7).getStringCellValue());
            String a=getCellValueByCell(row.getCell(8));
            lbmzj_yljgslxkzEntity.setFzsj(a);
            lbmzj_yljgslxkzEntity.setJglx(row.getCell(9).getStringCellValue());
            lbmzj_yljgslxkzEntity.setLx(row.getCell(10).getStringCellValue());
            lbmzj_yljgslxkzEntity.setGmjjhyfl(row.getCell(11).getStringCellValue());
            lbmzj_yljgslxkzEntity.setDh(row.getCell(12).getStringCellValue());
            lbmzj_yljgslxkzEntity.setZzjgdm(row.getCell(13).getStringCellValue());
            lbmzj_yljgslxkzEntity.setDwszdxzqhd(row.getCell(14).getStringCellValue());
            lbmzj_yljgslxkzEntity.setXbzt(row.getCell(15).getStringCellValue());
            lbmzj_yljgslxkzEntity.setYb(row.getCell(16).getStringCellValue());
            list.add(lbmzj_yljgslxkzEntity);
        }
        return list;
    }
}
*/
