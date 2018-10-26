package constant;

import java.io.UnsupportedEncodingException;

public class Tips {

    public static final String httpResponse =
            "欢迎登录，JNIO聊天系统功能列表：\r\n" +
                    "1、群聊、\r\n" +
                    "2、私聊、\r\n" +
                    "3、改昵称、\r\n" +
                    "4、查看群聊、\r\n" +
                    "5、建群、\r\n" +
                    "6、传文件、\r\n" +
                    "7、传图片\r\n";

    public static  byte[] httpResponseBytes;

    static {
        try {
            httpResponseBytes = httpResponse.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
