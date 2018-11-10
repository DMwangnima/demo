package cn.nuofankj.myblog.handler;

import cn.nuofankj.fun.constant.ActionType;
import cn.nuofankj.fun.constant.FriendTipData;
import cn.nuofankj.fun.entity.KeepEnt;
import cn.nuofankj.fun.helper.WechatProcess;
import cn.nuofankj.fun.helper.WechatProcessDapter;
import cn.nuofankj.fun.repository.KeepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class KeepActionHandler extends ActionHandler {

    @Autowired
    private KeepRepository keepRepository;

    private String actionWord = "#健身打卡#，健身时长(60)分钟，我爱健身~";

    @Override
    public boolean isActionEnum(String xml) {

        String content = WechatProcess.processWechat(xml);

        if (content.contains("健身打卡")) {
            return true;
        }
        return false;
    }

    @Override
    public Object doAction(String xml) {

        String result;
        try {
            String content = WechatProcess.processWechat(xml);
            if (!content.contains("#健身打卡#")) {
                return actionWord;
            }

            int keepTime = Integer.parseInt(content.substring(content.indexOf("(") + 1, content.indexOf(")")));
            content = content.replace("#", "").replace("(", "").replace(")", "");
            String openid = WechatProcessDapter.getOpenidFromXml(xml);
            keepRepository.save(
                new KeepEnt().setOpenId(openid).setStartTime(System.currentTimeMillis()).setStartText(content).setKeepTime(keepTime)
            );
            result = FriendTipData.KEEP_SUCCESS;
        } catch (Exception e) {
            result = FriendTipData.KEEP_ERROR;
        }
        return result;
    }

    @Override
    public int getActionType() {
        return ActionType.KEEP_ACTION;
    }

    @Override
    public String getActionWord() {
        return actionWord;
    }
}
