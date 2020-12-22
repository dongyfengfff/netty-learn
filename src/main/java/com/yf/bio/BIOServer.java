package com.yf.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO demo
 *
 * 本地调试
 *  telnet 127.0.0.1 6666
 *  send + 数据内容
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {

        // 创建一个线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        // 创建 serverSocket
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("server started !!!");

        while (true) {

            // 监听，等待连接
            System.out.println("Wait for connection ");
            final Socket socket = serverSocket.accept();
            System.out.println("Connect to a client");

            // 创建一个线程，进行通信
            newCachedThreadPool.execute(new Runnable(){

                public void run() {

                    // TODO 与客户端通信 code
                    handler(socket);

                }
            });
        }
    }

    /**
     * 与客户端进行通信
     * @param socket
     */
    public static void handler(Socket socket){

        try {

            System.out.println("thread information : " + Thread.currentThread().getId() +
                    "  ||  thread name : " + Thread.currentThread().getName());

            byte[] bytes = new byte[1024];
            // 通过 socket , 获取输入流
            InputStream inputStream = socket.getInputStream();

            // 循环读取客户端 发送的数据
            while (true) {

                System.out.println("thread information : " + Thread.currentThread().getId() +
                        "  ||  thread name : " + Thread.currentThread().getName());

                System.out.println("read ...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    // todo 此处直接输出客户端发送的数据
                    System.out.println(new String(bytes , 0 , read));
                } else {

                    break;
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            // 关闭和 client 的连接
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}
