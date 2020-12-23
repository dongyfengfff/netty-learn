package com.yf.nio;

import java.nio.ByteBuffer;

/**
 * 类型化方式存取数据
 */
public class NIOByteBufferPutGet {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(64);

        // 类型化方式存数据
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('锋');
        buffer.putShort((short)4);

        // 取出
        buffer.flip();

        System.out.println("take out ...");

        // 按顺序一一类型 读取
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());


        //System.out.println(buffer.getLong());
        /**
         * Exception in thread "main" java.nio.BufferUnderflowException
         * 	at java.nio.Buffer.nextGetIndex(Buffer.java:506)
         * 	at java.nio.HeapByteBuffer.getLong(HeapByteBuffer.java:412)
         * 	at com.yf.nio.NIOByteBufferPutGet.main(NIOByteBufferPutGet.java:29)
         *
         * 	按照类型获取
         */

    }
}
