package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 *  EchoServerHandler 实现了业务逻辑;
 *  main() 方法引导了服务器;
 *  引导过程中所需要的步骤如下:
 *      创建一个 ServerBootstrap 的实例以引导和绑定服务器;
 *      创建并分配一个 NioEventLoopGroup 实例以进行事件的处理,如接受新连接以及读/写数据;
 *      指定服务器绑定的本地的 InetSocketAddress ;
 *      使用一个 EchoServerHandler 的实例初始化每一个新的 Channel ;
 *      调用 ServerBootstrap.bind() 方法以绑定服务器。
 */

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(("Usage: " + EchoServer.class.getSimpleName() +" <port>"));
        }

        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    public void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // NIO 传输
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(group).channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(serverHandler);
                    }
                });
        // 导致当前 Thread阻塞,一直到绑定操作完成为止
        ChannelFuture f = b.bind().sync();
        f.channel().closeFuture().sync();

    }
}