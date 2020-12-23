package com.yf.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件拷贝
 */
public class FileChannel04 {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream1 = new FileInputStream("C:\\Users\\15503\\Desktop\\ex.jpg");
        FileChannel sourceChannel = fileInputStream1.getChannel();

        FileOutputStream fileOutputStream2 = new FileOutputStream("C:\\Users\\15503\\Desktop\\my_rep\\netty-learn\\src\\main\\resources\\execution.jpg");
        FileChannel destChannel = fileOutputStream2.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 使用 transferForm 完成拷贝
        destChannel.transferFrom(sourceChannel , 0 , sourceChannel.size());

        // stop
        sourceChannel.close();
        destChannel.close();
        fileInputStream1.close();
        fileOutputStream2.close();
    }
}
