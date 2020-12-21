package com.yf.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BIODemo {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("0.0.0.0" , 8080), 50);
        Socket socket;

        while ((socket = serverSocket.accept()) != null) {
            InputStream inputStream = socket.getInputStream();
            byte[]      data        = new byte[16];
            inputStream.read(data);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data);
            socket.close();
        }
    }
}
