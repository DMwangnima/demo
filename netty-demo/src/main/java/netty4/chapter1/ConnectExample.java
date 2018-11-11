package netty4.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class ConnectExample {

    // 提供基本的IO操作(bind()、connect()、read()和write())，简化socket的复杂性
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    public static void connect() {
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        // Netty中的所有IO操作都是异步的，因为一个操作不会立即返回的，所以需要一种用于在之后某个时间点确定结果的方法，
        // 为此，Netty提供了ChannelFuture接口，其addListener()方法注册了一个ChannelFutrueListener
        ChannelFuture channelFuture = channel.connect(new InetSocketAddress("127.0.0.1",8888));
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                // 检查操作的结果
                if(channelFuture.isSuccess()) {
                    // 创建一个BuyteBuff持有数据
                    ByteBuf buffer = Unpooled.copiedBuffer("Hello", Charset.defaultCharset());
                    // 将数据异地发送到远程节点，返回一个ChannelFuture
                    ChannelFuture wf = channelFuture.channel().writeAndFlush(buffer);
                } else {
                    // 如果发生错误
                    Throwable cause = channelFuture.cause();
                    cause.printStackTrace();
                }
            }
        });

    }
}
