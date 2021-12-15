package com.dianzi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test2 {
    public static void main(String[] args) {
        FileOutputStream stream = null;
        File file;//创建文件夹
        try {
            File filesl=new File("C:\\test\\xmxx.txt");
            InputStreamReader readersl=new InputStreamReader(new FileInputStream(filesl),"GBK");
            BufferedReader bufferedReadersl=new BufferedReader(readersl);
            String lineTxtsl=null;
            List listSL=new ArrayList();
             file = new File("C:\\test\\333.txt");
            stream = new FileOutputStream (file);
            while ((lineTxtsl=bufferedReadersl.readLine())!=null){
                String a=lineTxtsl.replaceAll("\"","\\`");
                System.out.println(a);
                listSL.add(a);
            }
            for (int i = 0; i < listSL.size(); i++) {
                byte[] contentInBytes = listSL.get(i).toString().getBytes();//转化成字节形
                stream.write(contentInBytes);//写入
            }
            stream.flush(); //写完之后刷新
            stream.close();//关闭流.
            //System.out.println(listSL);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
