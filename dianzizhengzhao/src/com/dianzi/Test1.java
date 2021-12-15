package com.dianzi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test1 {
    public static void main(String[] args) {
        try {
            File filesl=new File("C:\\test1\\shilimiandeshuju.txt");
            File filexl=new File("C:\\test1\\bendixianlishuju.txt");
            InputStreamReader readersl=new InputStreamReader(new FileInputStream(filesl),"UTF-8");
            BufferedReader bufferedReadersl=new BufferedReader(readersl);
            InputStreamReader readerxl=new InputStreamReader(new FileInputStream(filexl),"UTF-8");
            BufferedReader bufferedReaderxl=new BufferedReader(readerxl);
            String lineTxtsl=null;
            String lineTxtxl=null;
            List listSL=new ArrayList();
            List listXL=new ArrayList();
            while ((lineTxtsl=bufferedReadersl.readLine())!=null){
                listSL.add(lineTxtsl);
            }
            while ((lineTxtxl=bufferedReaderxl.readLine())!=null){
                listXL.add(lineTxtxl);
            }
            List duibi=new ArrayList();
            for (int i = 0; i < listSL.size(); i++) {
                if (listXL.contains(listSL.get(i))){
                    continue;
                }else {
                    duibi.add(listSL.get(i));
                }
            }


            System.out.println(listSL);
            System.out.println(listXL);
            System.out.println(duibi.stream().distinct().collect(Collectors.toList()));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
