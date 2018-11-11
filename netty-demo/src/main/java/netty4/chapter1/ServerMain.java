package netty4.chapter1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) throws IOException {
        new ServerMain().bing(20001);
    }

    public void bing(int port) throws IOException {

        // 创建ServerSocket监听port端口
        ServerSocket serverSocket = new ServerSocket(port);

        // 接受一个连接并构建出对应的客户端socket
        Socket clientSocket = serverSocket.accept();

        BufferedReader bufferReader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
        );

        PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(),true);

        String request;

        while((request = bufferReader.readLine()) != null) {
            // 如果客户端发送了Done，则结束循环
            if("Done".equals(request)) {
                break;
            }

            printWriter.println(processRequest(request));

        }

    }

    public String processRequest(String request) {
        return "服务端接收到的文字是："+request;
    }
}
