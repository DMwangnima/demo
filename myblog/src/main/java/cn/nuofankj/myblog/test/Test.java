package cn.nuofankj.myblog.test;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

public class Test {

    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        if(!file.exists()) {
            return null;
        }
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String s = "/home/ricoder/code/java/t-fun/fun/src/main/java/cn/nuofankj/fun/job/crawler/js/video/video_1.html";
        File file = new File(s);
        if(file.exists()) {
            String content = readToString(s);
            Document doc = Jsoup.parse(content);
            System.out.println(doc.getElementById("dplayer").toString());
        }

    }

}