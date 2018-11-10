package cn.nuofankj.myblog.helper;

import cn.nuofankj.fun.handler.ActionHandler;
import cn.nuofankj.fun.pojo.ReceiveXmlEntity;

import java.util.Map;
import java.util.Set;

/**
 * Created by 稀饭 on 2017/5/25.
 */
public class WechatProcessDapter extends WechatProcess {

    /**
     * 判断是否为功能数据，如代金券功能
     *
     * @param xml
     * @return 判断如果是功能数据，则返回true，如果不是，则返回false
     */
    public static boolean checkWechatMag(String xml) {
        boolean check = false;
        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = ReceiveXmlProcess.getMsgEntity(xml);
        if ("text".endsWith(xmlEntity.getMsgType())) {
            String message = xmlEntity.getContent();
            if (message.contains("领取+")) {
                check = true;
            }
        }
        return check;
    }

    public static Object doAction(String xml) {
        Map<Integer, ActionHandler> actionHandlerMap = ActionHandler.actionHandlerMap;
        Set<Integer> types = actionHandlerMap.keySet();
        for (int type : types) {
            ActionHandler actionHandler = actionHandlerMap.get(type);
            if (actionHandler.isActionEnum(xml)) {
                return actionHandler.doAction(xml);
            }
        }

        String result = "";
        int index = 1;
        for (int type : types) {
            ActionHandler actionHandler = actionHandlerMap.get(type);
            result += actionHandler.getActionWord();
            if (index < types.size()) {
                result = result + "\n";
            }
            index++;
        }
        return result;
    }


    /**
     * 获取xml里边的内容
     *
     * @param xml
     * @return
     */
    public static String getContentFromXML(String xml) {
        ReceiveXmlEntity xmlEntity = ReceiveXmlProcess.getMsgEntity(xml);
        return xmlEntity.getContent();
    }

    /**
     * 获取用户的openid
     *
     * @param xml
     * @return string
     */
    public static String getOpenidFromXml(String xml) {
        String openid = null;
        ReceiveXmlEntity xmlEntity = ReceiveXmlProcess.getMsgEntity(xml);
        if (xmlEntity != null) {
            openid = xmlEntity.getFromUserName();
        }
        return openid;
    }

    /**
     * 处理一般性文本数据，并调用图灵回复
     *
     * @param xml
     * @return
     */
    public static String textWechatMag(String xml) {
        return processWechatMag(xml);
    }

    /**
     * 处理一般性文本数据,从xml中取出content
     *
     * @param xml
     * @return
     */
    public static String textWechat(String xml) {
        return processWechat(xml);
    }

    /**
     * 将文字处理成微信xml
     *
     * @param result
     * @return
     */
    public static String wordToXML(ReceiveXmlEntity xmlEntity, String result) {
        result = FormatXmlProcess.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
        return result;
    }

    /**
     * 将文字处理成微信xml
     *
     * @param result
     * @return
     */
    public static String wordToXML(String xml, String result) {
        ReceiveXmlEntity xmlEntity = ReceiveXmlProcess.getMsgEntity(xml);
        result = FormatXmlProcess.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
        return result;
    }
}
