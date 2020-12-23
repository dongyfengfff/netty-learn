package com.yf.nio;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel 案例3
 * 从文件读写数据
 */
public class FileChannel03 {

    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream1 = new FileInputStream("C:\\Users\\15503\\Desktop\\my_rep\\netty-learn\\src\\main\\resources\\file03_1.txt");
        FileChannel channel1 = fileInputStream1.getChannel();

        FileOutputStream fileOutputStream2 = new FileOutputStream("C:\\Users\\15503\\Desktop\\my_rep\\netty-learn\\src\\main\\resources\\file03_2.txt");
        FileChannel channel2 = fileOutputStream2.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (true) {
            // todo 切记 清空
            byteBuffer.clear(); // todo 清空是 position , limit 的操作 , 缺少该语句，会进行无线循环
            int read = channel1.read(byteBuffer);
            if(read == -1) break;

            // 将 buffer 中的数据写入 channel2
            byteBuffer.flip();// todo 切记翻转
            channel2.write(byteBuffer);
        }

        fileInputStream1.close();
        fileOutputStream2.close();

    }

}
