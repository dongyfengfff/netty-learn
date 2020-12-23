package com.yf.nio;

import java.nio.ByteBuffer;

/**
 * readonly buffer
 */
public class ReadOnlyBuffer {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(64);

        for(int i = 0 ; i < 64 ; i++) {
            buffer.put((byte) i);
        }

        // flip
        buffer.flip();

        // 得到一个只读的 buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());  // class java.nio.HeapByteBufferR

        // read
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }

        // todo test write
        readOnlyBuffer.put((byte) 100); // 会有异常抛出
        /**
         * Exception in thread "main" java.nio.ReadOnlyBufferException
         * 	at java.nio.HeapByteBufferR.put(HeapByteBufferR.java:172)
         * 	at com.yf.nio.ReadOnlyBuffer.main(ReadOnlyBuffer.java:31)
         */
    }
}
