package com.yf.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝一次
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile(
                "C:\\Users\\15503\\Desktop\\my_rep\\netty-learn\\src\\main\\resources\\file01.txt", "rw");

        // 获取对应的文件通道
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * FileChannel.MapMode.READ_WRITE ==> 读写模式
         * 0 ==> 可以修改的起始位置
         * 5 ==> 
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        map.put(0 , (byte) 'F');
        map.put(3,(byte) 'F');
        System.out.println("test size ...");
        //map.put(5 , (byte) 'F');

        /**
         * test size ...
         * Exception in thread "main" java.lang.IndexOutOfBoundsException
         * 	at java.nio.Buffer.checkIndex(Buffer.java:540)
         * 	at java.nio.DirectByteBuffer.put(DirectByteBuffer.java:306)
         * 	at com.yf.nio.MappedByteBufferTest.main(MappedByteBufferTest.java:31)
         */

        randomAccessFile.close();
        System.out.println("modify success !");
        // todo 要在 path 用编辑器打开，或者 重启idea 才可以看到


    }
}
