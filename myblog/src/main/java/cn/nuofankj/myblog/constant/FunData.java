package cn.nuofankj.myblog.constant;

import cn.nuofankj.fun.dto.impl.HotVideoDto;
import cn.nuofankj.fun.dto.impl.PageVideoDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 稀饭 on 2017/5/25.
 */
public class FunData {

    /** 微信 **/
    public static String TOKEN = "ricoder";
    public static String APPID = "wx049a876577d71e82";
    public static String APPSECRET = "78e54546b5b5523fcd6277ea2fdbbb46";


    /** 影视 **/
    // value titleCn, key code
    public static Map<String, String> codeTitle = new HashMap<>();
    // key code, value pageVideo
    public static Map<String, PageVideoDto> pageVideoMap = new HashMap<>();
    // key page, value hotVideoDTOs
    public static Map<Integer, List<HotVideoDto>> pageVideoDtoMap = new HashMap<>();

    // 视频访问接口地址
    public static String viewUrl = "http://localhost:8088/api/video?c=PSWCODE";
}
