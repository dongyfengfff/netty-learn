package com.yf.nio;

import java.nio.IntBuffer;

/**
 * buffer 的使用
 *
 * 八种基本数据类型，除了 boolean ，都有对应的 buffer
 * byteBuffer 最为重要
 */
public class BasicBuffer {

    public static void main(String[] args) {

        /**
         * private int mark = -1;
         * private int position = 0;
         * private int limit;
         * private int capacity;
         *     buffer 提供上面四个属性
         */

        // 创建 buffer , 可以存放 5 个 ， capacity 是 5
        IntBuffer intBuffer = IntBuffer.allocate(5);  // position 置 0 ， limit = 5 类似 数组的 size 属性 最大索引加一,

        intBuffer.put(10);
        intBuffer.put(11);
        intBuffer.put(12);
        intBuffer.put(13);
        intBuffer.put(14);  // position 类比 动态维护的 size , 此处 position = 5 == limit

        // 从 buffer 读取数据
        intBuffer.flip();//todo 将 buffer 转换，读写切换

        intBuffer.position(1); // todo 可以设置开始读的位置，表示底层从 数组的第二个元素开始读  即 11 12 13 14
        intBuffer.limit(3); // todo  限制读到的位置，理解为数组容量，和 数组实际存放的数据，并设置limit 开始，之后的数据无法访问。即 11 12
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
