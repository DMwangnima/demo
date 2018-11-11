package netty5.chapter1;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
 
public class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
 
	@Override
	protected void initChannel(SocketChannel e) throws Exception {
 
		System.out.println("报告");
		System.out.println("信息：有一客户端链接到本服务端");
		System.out.println("IP:"+e.localAddress().getHostName());
		System.out.println("Port:"+e.localAddress().getPort());
		System.out.println("报告完毕");
	
	}
 
}