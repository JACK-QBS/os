package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
    public static void main(String[] args) throws IOException {
        File input = new File("D:\\Code\\os\\src\\io\\FileInput.java");
        File output = new File("D:\\Code\\os\\src\\io\\copy.java");
        long start = System.currentTimeMillis();
        if (!output.exists()) {
            output.createNewFile();
        }
        //定义输入输出流
        FileInputStream fis = new FileInputStream(input);
        FileOutputStream fos = new FileOutputStream(output);
        byte[] bytes = new byte[1024*8];
        int len;
        //每次从输入流读取到byte[]的内容，直接输出到某个文件，就是复制
        while ((len = fis.read(bytes)) != -1) {
            fos.write(bytes,0,len);
        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        fis.close();
        fos.close();
    }
}
