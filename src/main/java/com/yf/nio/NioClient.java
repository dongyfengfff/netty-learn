package com.yf.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * client
 */
public class NioClient {
    public static void main(String[] args) throws Exception{

        // 得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();

        // 设置非阻塞模式
        socketChannel.configureBlocking(false);

        // 提供服务器端的 ip 和 port
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        // todo 连接服务器
        if (! socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }

        // 连接成功，发送数据
        String str = "hello , dongyfengfff";
        // wrap a byte array into a buffer ==> 包裹
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());

        // send data  => 将 buffer 数据写入 channel
        socketChannel.write(buffer);
        System.in.read();

    }
}
