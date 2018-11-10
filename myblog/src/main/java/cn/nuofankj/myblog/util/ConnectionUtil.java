package cn.nuofankj.myblog.util;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("deprecation")
public class ConnectionUtil {

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param loginAction 发送请求的URL
     * @param param       请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return String代表获取到的cookie
     */
    public static String getCookie(String param, String loginAction)
        throws Exception {
        OutputStream out = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        URL riceUrl = null;
        // 登录
        riceUrl = new URL(loginAction);
        // String param = "username="+username+"&password="+password;
        conn = (HttpURLConnection) riceUrl.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        out = conn.getOutputStream();
        out.write(param.getBytes());
        out.flush();
        out.close();
        String sessionId = "";
        String cookieVal = "";
        String key = null;
        // 取cookie
        for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
            if (key.equalsIgnoreCase("set-cookie")) {
                cookieVal = conn.getHeaderField(i);
                cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
                sessionId = sessionId + cookieVal + ";";
            }
        }
        return sessionId;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 向指定URL发送get方法的请求
     *
     * @param url             发送请求的URL
     * @param requestProperty header必備的些許數據。:后記得多加個空格
     */
    public static String sendGet(String url, String[] requestProperty) {
        PrintWriter out = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        URL riceUrl = null;
        String result = null;
        try {
            riceUrl = new URL(url);
        } catch (MalformedURLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        // 打开和URL之间的连接
        try {
            conn = (HttpURLConnection) riceUrl.openConnection();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        // System.out
        // .println("===================這是require的header========================");
        if (requestProperty != null) {
            for (int i = 0; i < requestProperty.length; i++) {
                String[] index = requestProperty[i].split(": ");
                // System.out.println(index[0] + "======>" + index[1]);
                conn.setRequestProperty(index[0], index[1]);
            }
        }
        // conn.setDoOutput(true);
        // conn.setDoInput(true);
        // conn.setConnectTimeout(3 * 1000);
        conn.setReadTimeout(3 * 1000);
        try {
            if (conn != null)
                in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "utf-8"));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (in != null) {
            /* 输出response的header */
            // Map<String, List<String>> map = conn.getHeaderFields();
            // Set set = map.keySet();
            // Iterator<String> iterator = set.iterator();
            // System.out
            // .println("===================這是response的header========================");
            // for (; iterator.hasNext();) {
            // String key = (String) iterator.next();
            // List<String> list = map.get(key);
            // StringBuilder builder = new StringBuilder();
            // for (String str : list) {
            // builder.append(str).toString();
            // }
            // String firstCookie = builder.toString();
            // System.out.println(key = "===>" + firstCookie);
            // }
            String line = null;
            /* 获取response的值 */
            try {
                while ((line = in.readLine()) != null) {
                    result += "\n" + line;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url             发送请求的URL
     * @param param           请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @param requestProperty header必備的些許數據。
     * @return String所代表远程资源的响应
     */
    public static String sendPost(String url, String[] requestProperty,
                                  String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        URL riceUrl = null;
        try {
            riceUrl = new URL(url);
        } catch (MalformedURLException e2) {
            // TODO Auto-generated catch block
            // e2.printStackTrace();
        }
        // 打开和URL之间的连接
        try {
            conn = (HttpURLConnection) riceUrl.openConnection();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            // e2.printStackTrace();
        }
        if (requestProperty != null) {
            for (int i = 0; i < requestProperty.length; i++) {
                String[] index = requestProperty[i].split(": ");
                System.out.println(index[0] + "======>" + index[1]);
                conn.setRequestProperty(index[0], index[1]);
            }
        }
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);

        // 获取URLConnection对象对应的输出流
        try {
            out = new PrintWriter(conn.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(param);
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        try {
            in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String line = null;
        String result = null;
        try {
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, List<String>> map = conn.getHeaderFields();
        Set<String> set = map.keySet();
        Iterator iterator = set.iterator();
        System.out.println("==================这是response的header=============");
        for (; iterator.hasNext(); ) {
            String key = (String) iterator.next();
            List<String> list = map.get(key);
            StringBuilder builder = new StringBuilder();
            for (String str : list) {
                builder.append(str).toString();
            }
            String firstCookie = builder.toString();
            System.out.println(key + "=====>" + firstCookie);
        }
        return result;
    }
}
