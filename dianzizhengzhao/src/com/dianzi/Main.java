package com.dianzi;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            File file=new File("C:\\test1\\dianzizhengzhao.txt");
            InputStreamReader reader=new InputStreamReader(new FileInputStream(file),"GBK");
            BufferedReader bufferedReader=new BufferedReader(reader);
            String lineTxt=null;
            while ((lineTxt=bufferedReader.readLine())!=null){
              System.out.println("select count(*) from "+ lineTxt +" UNION ALL");
            }reader.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	//读取txt中的文件

    }
}
