package com.dianzi;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Test {
    public static void main(String[] args) {
        try {
            File file=new File("C:\\test1\\xuyaozhuanderiqi.txt");
            InputStreamReader reader=new InputStreamReader(new FileInputStream(file),"GBK");
            BufferedReader bufferedReader=new BufferedReader(reader);
            String lineTxt=null;
            while ((lineTxt=bufferedReader.readLine())!=null){
                int linexTxt=Integer.parseInt(lineTxt);
                Date date=doubleToDate(linexTxt);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY/MM/dd");
               System.out.println(simpleDateFormat.format(date));
             }reader.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Date doubleToDate(int dateTime) {
        Calendar base = Calendar.getInstance();
        //Delphi的日期类型从1899-12-30 开始
        base.set(1899, 11, 30, 0, 0, 0);
        base.add(Calendar.DATE, dateTime);
        base.add(Calendar.MILLISECOND,(int)((dateTime % 1) * 24 * 60 * 60 * 1000));
        return base.getTime();
    }
}
