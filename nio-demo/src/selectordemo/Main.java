package selectordemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

public class Main {
    public static void main(String[] args) throws IOException {
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 38\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body>Hello World!</body></html>";

        byte[] httpResponseBytes = httpResponse.getBytes("UTF-8");
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(9999));
        serverSocket.configureBlocking(false);

    }
}
