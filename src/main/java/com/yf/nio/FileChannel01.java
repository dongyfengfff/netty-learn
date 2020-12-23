package com.yf.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileChannel 案例
 * 向文件写数据
 */
public class FileChannel01 {

    public static void main(String[] args) throws IOException {

        String srt = "hello , dongyfengfff , this is my netty learn rep";

        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream(
                "C:\\Users\\15503\\Desktop\\my_rep\\netty-learn\\src\\main\\resources\\file01.txt");

        // 通过输出流获取对应的 fileChannel todo 真实类型是 FileChannelImpl
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        // 创建缓冲区  todo 在此处打点 debug ，查看
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 将 str 放进 byteBuffer
        byteBuffer.put(srt.getBytes());

        // 对 byteBuffer 进行翻转
        byteBuffer.flip();

        // todo 将 byteBuffer 写入到 fileChannel
        fileOutputStreamChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
