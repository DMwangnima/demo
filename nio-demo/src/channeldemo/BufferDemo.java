package channeldemo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile aFile = new RandomAccessFile("D:\\java\\zawu\\test.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        // 设置ByteBuffer容量capacity最大为48
        ByteBuffer buf = ByteBuffer.allocate(48);
        // 从FileChannel读取数据写入ByteBuffer
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            // 将ByteBuffer模式从写入模式切换为读取模式
            buf.flip();
            // 判断其中是否还有数据待读取，查看源码position < limit
            // 每次读取一个字节
            System.out.print((char) buf.get());

            // 清空buf
            buf.compact();
            // 此处不需要做flip切换吗？
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
