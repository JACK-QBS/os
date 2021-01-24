package io;

import java.io.*;

public class FileInput {
    public static void main(String[] args) throws IOException {

        File file1 = new File("D:\\Code\\os\\src\\dog.jpg");
        //1、文件输入字节流
        FileInputStream fis1 = new FileInputStream(file1);
        //输入流比较固定的写法：读取到一个字节/字符数组，定义read的返回值变量,while
        byte[] bytes = new byte[1024];
        int len1 = 0;
        //读取到的长度，数组可能读满，也可能没读满，当次读取的内容，一般使用数组[0,len]
        while ((len1 = fis1.read(bytes)) != -1) {
            String str =  new String(bytes,0,len1);//模拟
            System.out.println(str);
        }


        File file2 = new File("D:\\Code\\os\\src\\io\\hhh.txt");
        //2、文件输入字符流：
        FileReader fr = new FileReader(file2);
        char[] chars = new char[1024];
        int len2 = 0;
        while ((len2 = fr.read(chars)) != -1) {
            String str = new String(chars,0,len2);
            System.out.println(str);
        }


        File file = new File("D:\\Code\\os\\src\\io\\hhh.txt");
        //3、缓存流：缓存字节输入、缓存字符输入
        FileInputStream fis = new FileInputStream(file);//文件字节输入流
        //字节流转字符流，一定要经过字节字符转换流来转换，并且可以制定编码
        //和文件编码格式要一致，否则会是乱码
        InputStreamReader isr = new InputStreamReader(fis,"utf-8");
        BufferedReader br = new BufferedReader(isr);
        String str;
        while ((str = br.readLine()) != null){
            System.out.println(str);
        }
        //释放资源，反向释放
        br.close();
        isr.close();
        fis.close();
    }
}
