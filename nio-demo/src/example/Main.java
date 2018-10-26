package example;

import constant.Tips;
import http.HttpMessageReaderFactory;
import core.IMessageProcessor;
import core.Message;
import core.Server;

import java.io.IOException;


/**
 * Created by jjenkov on 19-10-2015.
 */
public class Main {

    public static void main(String[] args) throws IOException {


        IMessageProcessor messageProcessor = (request, writeProxy) -> {
            System.out.println("core.Message Received from socket: " + request.socketId);

            Message response = writeProxy.getMessage();
            response.socketId = request.socketId;
            response.writeToMessage(Tips.httpResponseBytes);

            writeProxy.enqueue(response);
        };

        Server server = new Server(9999, new HttpMessageReaderFactory(), messageProcessor);

        server.start();

    }


}
