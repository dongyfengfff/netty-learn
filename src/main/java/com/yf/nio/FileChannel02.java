package com.yf.nio;


import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.lang.String;

/**
 * FileChannel 案例2
 * 从文件读数据
 */
public class FileChannel02 {

    public static void main(String[] args) throws Exception {

        // 创建文件输入流
        File file = new File(
                "C:\\Users\\15503\\Desktop\\my_rep\\netty-learn\\src\\main\\resources\\file01.txt"
        );
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 将 channel 数据读入到 buffer 中
        fileChannel.read(byteBuffer);

        // 将 byteBuffer 中的字节数据 => String
        System.out.println(new String(byteBuffer.array()));

        // 关闭
        fileInputStream.close();

    }
}
