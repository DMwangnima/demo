package channeldemo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\java\\idea\\demo\\.gitignore", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        int bytesRead = fileChannel.read(byteBuffer);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            byteBuffer.flip();
            while(byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
            byteBuffer.clear();
            bytesRead = fileChannel.read(byteBuffer);
        }
        randomAccessFile.close();
    }
}
