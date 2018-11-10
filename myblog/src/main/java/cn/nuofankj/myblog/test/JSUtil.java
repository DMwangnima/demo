package cn.nuofankj.myblog.test;

import org.xml.sax.SAXException;

import java.io.*;

public class JSUtil
{
  
    public static void main(String[] args) throws IOException, SAXException
    {
        // 测试调用。传入url即可  
        String html = getParseredHtml2();
        System.out.println("html: " + html);  
    }  
  
   // TODO 垃圾东西，直接用命令行就可以，用java去调就不行
    public static String getParseredHtml2() throws IOException
    {  
        Runtime rt = Runtime.getRuntime();  
        Process p = rt.exec( "phantomjs /home/ricoder/code/java/fun/src/main/java/cn/nuofankj/fun/test/js/codes.js --web-security=no");
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();  
        String tmp = "";  
        while ((tmp = br.readLine()) != null)  
        {  
            sbf.append(tmp);  
        }
        return sbf.toString();
    }  
  
}  