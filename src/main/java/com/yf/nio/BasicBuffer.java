package com.yf.nio;

import java.nio.IntBuffer;

/**
 * buffer 的使用
 */
public class BasicBuffer {

    public static void main(String[] args) {

        // 创建 buffer , 可以存放 5 个
        IntBuffer intBuffer = IntBuffer.allocate(5);

        intBuffer.put(10);
        intBuffer.put(11);
        intBuffer.put(12);
        intBuffer.put(13);
        intBuffer.put(14);

        // 从 buffer 读取数据
        intBuffer.flip();//todo 将 buffer 转换，读写切换
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
