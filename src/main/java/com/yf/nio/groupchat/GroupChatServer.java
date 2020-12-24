package com.yf.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * server
 */
public class GroupChatServer {

    // 属性
    private Selector selector;

    private ServerSocketChannel listenChannel;

    private static final int PORT = 6666;

    /**
     * 构造方法
     */
    public GroupChatServer() {

        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);

            // 将 listenChannel 注册到 selector 上
            listenChannel.register(selector , SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            // 循环 监听
            int count = selector.select(2000);

            if(count > 0) {  // TODO 说明有事件要处理

                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    // 取出 selectedkey
                    SelectionKey key = iterator.next();

                    // 监听到了 accept 事件
                    if (key.isAcceptable()) {
                        //
                        SocketChannel sc = listenChannel.accept();
                        // 将 该 sc 注册到 selector 上
                        sc.register(selector , SelectionKey.OP_READ);
                        // 提示
                        System.out.println(sc.getRemoteAddress() + " 上线了 ...");
                    }

                    // 监听到了
                    if (key.isReadable()) { // 通道发送 read 事件， 即通道是可读的状态
                        // todo 处理读的事件
                        readData(key);
                    }

                    // todo 当前的 key 删除，防止重复操作
                    iterator.remove();
                }

            }  else {
                System.out.println("等待中 ！！！");
            }


        } catch (Exception e){
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 读取客户端的消息
     * @param key
     */
    private void readData(SelectionKey key) {

        // 取到关联的 channel
        SocketChannel channel = null;

        try {
            channel = (SocketChannel) key.channel();
            // 创建 buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);

            // 根据 count 的值做处理
            if (count >  0) {
                // 把缓冲区的数据转成字符串
                String message = new String(buffer.array());
                System.out.println("from client : " + message);

                // todo 向其他的客户端转发消息
                sendInfoToOtherClients(message , channel);
            }

        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了 ...");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 转发消息给其他客户端
     * @param msg
     * @param self
     */
    private  void sendInfoToOtherClients(String msg , SocketChannel self) throws IOException {

        System.out.println("服务器转发消息中");

        // 遍历所有注册到 selector 上的 socketChannel , 并排除 self
        for (SelectionKey key : selector.keys()) {
            SelectableChannel targetChannel = key.channel();
            // 排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self) {

                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将 message 存储到 buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {

        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
