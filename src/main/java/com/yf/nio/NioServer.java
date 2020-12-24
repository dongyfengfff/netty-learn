package com.yf.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * server
 */
public class NioServer {

    public static void main(String[] args) throws IOException {

        // 创建 serverSocketChannel -> serverSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // get Selector object
        Selector selector = Selector.open();

        // bind a port，在 sever 端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);

        // 把 serverSocketChannel 注册到 selector  关心的事件为 OP_ACCEPT
        serverSocketChannel.register(selector , SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {
            // wait for a second , if have no event , return
            if(selector.select(1000) == 0) {
                System.out.println("server waited for a second , no connected");
                continue;
            }

            // 返回的 > 0 , 就获取到相关的 selectionkey 集合
            // selector.selectedkeys() 返回关注事件的集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

            while (keyIterator.hasNext()) {

                SelectionKey key = keyIterator.next();

                if(key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    //todo ****  设置非阻塞
                    socketChannel.configureBlocking(false);

                    System.out.println("client connected success !!! 生成了一个 socketChannel " + socketChannel.hashCode());

                    socketChannel.register(selector , SelectionKey.OP_READ , ByteBuffer.allocate(1024));
                }

                if (key.isReadable()) {  // 发生 OP_READ
                    // 通过 key 反向获取对应的 channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取到 该 channel 关联的 buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("form client " + new String(buffer.array()));
                }

                // 手动从集合中移除当前的 selectionKey , 防止重复操作
                keyIterator.remove();
            }
        }
    }
}
