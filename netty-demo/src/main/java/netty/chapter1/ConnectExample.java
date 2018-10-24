package netty.chapter1;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;


public class ConnectExample {

    // 基本的IO操作（bind、connect、read、write）依赖于底层网络传输实现，在java中，是socket的主要实现，而channel所提供的api
    // 大大减低了直接使用socket的复杂性，而此处的NioSocketChannle便是channel的某个实现
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void connect() {

        Channel channel = CHANNEL_FROM_SOMEWHERE;

        // Netty中的所有I/O操作都是异步的，
        ChannelFuture future = channel.connect(new InetSocketAddress("127.0.0.1",8888));


    }


}
