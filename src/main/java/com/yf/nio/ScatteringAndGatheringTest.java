package com.yf.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * scattering 将数据写入到 buffer 时，采用 buffer 数组，依次写入
 * Gathering => 从 buffer 读取数据时，采用 buffer 数组，依次读
 */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws IOException {

        // 使用 ServerSocketChannel 和 SocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);

        // bind port to socket , and start
        serverSocketChannel.socket().bind(inetSocketAddress);

        // 创建 buffer array
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // wait for the connection from the client
        SocketChannel socketChannel = serverSocketChannel.accept();

        int messageLength = 8;  // 假定从客户端接收 8 个字节的数据

        // loop read
        while (true) {
            int byteRead = 0;

            while (byteRead < messageLength) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l;//累计读取的字节数s
                System.out.println("byteRead = " + byteRead);
                // use stream print and look for the position and the limit of the buffer
                Arrays.asList(byteBuffers).stream().map(buffer -> "position => " + buffer.position() + " , limit => " + buffer.limit()).forEach(System.out::println);

            }

            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

            // 将数据读出，显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength) {
                socketChannel.write(byteBuffers);
                byteWrite += 1;
            }

            // buffer clear
            Arrays.asList(byteBuffers).forEach(buffer -> {
                buffer.clear();
            });

            System.out.println("byteRead := " + byteRead + "    byteWrite := " + byteWrite + "    messageLength := " + messageLength);
        }
    }
}
