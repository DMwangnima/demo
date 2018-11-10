package cn.nuofankj.myblog.handler;

import cn.nuofankj.fun.constant.ActionType;
import cn.nuofankj.fun.constant.FriendTipData;
import cn.nuofankj.fun.entity.QuotesEnt;
import cn.nuofankj.fun.helper.WechatProcess;
import cn.nuofankj.fun.helper.WechatProcessDapter;
import cn.nuofankj.fun.repository.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuotesActionHandler extends ActionHandler {

    @Autowired
    private QuotesRepository quotesRepository;

    private String actionWord = "#微语录#，你要的全带~";

    @Override
    public boolean isActionEnum(String xml) {
        String content = WechatProcess.processWechat(xml);

        if (content.contains("微语录")) {
            return true;
        }
        return false;
    }

    @Override
    public Object doAction(String xml) {
        String result;
        try {
            String content = WechatProcess.processWechat(xml);
            if (!content.contains("#微语录#")) {
                return actionWord;
            }

            content = content.replace("#", "").replace("(", "").replace(")", "");
            String openid = WechatProcessDapter.getOpenidFromXml(xml);
            quotesRepository.save(
                new QuotesEnt().setText(content).setOpenId(openid)
            );
            result = FriendTipData.QUOTES_SUCCESS;
        } catch (Exception e) {
            result = FriendTipData.QUOTES_ERROR;
        }
        return result;
    }

    @Override
    public int getActionType() {
        return ActionType.QUOTES_ACTION;
    }

    @Override
    public String getActionWord() {
        return actionWord;
    }
}
