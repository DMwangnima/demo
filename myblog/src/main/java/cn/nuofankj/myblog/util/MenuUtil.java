package cn.nuofankj.myblog.util;

import cn.nuofankj.fun.constant.FunData;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 微信的菜单操作工具
 */
public class MenuUtil {

    /**
     * 创建Menu
     *
     * @param @return
     * @param @throws IOException    设定文件
     * @return int    返回类型
     * @throws
     * @Title: createMenu
     * @Description: 创建Menu
     */
    public static String createMenu() {

        //此处改为自己想要的结构体，替换即可
        String menu = "{\"button\":[{\"name\":\"联系我们\",\"sub_button\":[{\"type\":\"view\",\"name\":\"关于我们\",\"url\":\"http://nuofankj.cn/nuofan/index.html\"},{\"type\":\"click\",\"name\":\"用户反馈\",\"key\":\"01_FEEDBACK\"},{\"type\":\"click\",\"name\":\"广告合作\",\"key\":\"01_ADVER\"},{\"type\":\"click\",\"name\":\"商家合作\",\"key\":\"01_MERCHANTS\"}]}, {\"name\":\"功能介绍\",\"sub_button\":[{\"type\":\"click\",\"name\":\"代金券\",\"key\":\"02_CASH_COUPON\"}]}, {\"name\":\"应用下载\",\"sub_button\":[{\"type\":\"view\",\"name\":\"app\",\"url\":\"http://nuofankj.cn/idai/index.html\"}]}]}";
        String access_token = getAccess_token();

        String action = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            os.write(menu.getBytes("UTF-8"));//传入参数
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            return "返回信息" + message;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "createMenu 失败";
    }

    /**
     * 删除当前Menu
     *
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: deleteMenu
     * @Description: 删除当前Menu
     */
    public static String deleteMenu() {
        String access_token = getAccess_token();
        String action = "https://api.weixin.qq.com/cgi-bin/menu/delete? access_token=" + access_token;
        try {
            URL url = new URL(action);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒

            http.connect();
            OutputStream os = http.getOutputStream();
            os.flush();
            os.close();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            return "deleteMenu返回信息:" + message;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "deleteMenu 失败";
    }

    /**
     * 获得ACCESS_TOKEN
     *
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getAccess_token
     * @Description: 获得ACCESS_TOKEN
     */
    private static String getAccess_token() {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + FunData.APPID + "&secret=" + FunData.APPSECRET;
        String accessToken = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

            http.setRequestMethod("GET");      //必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒

            http.connect();

            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.parseObject(message);
            accessToken = demoJson.getString("access_token");

            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    public static void main(String[] args) {

        System.out.println(createMenu());
    }
}
