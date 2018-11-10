package cn.nuofankj.myblog.handler;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


public abstract class ActionHandler {

    public static Map<Integer, ActionHandler> actionHandlerMap = new HashMap<>();

    @PostConstruct
    private void init() {
        actionHandlerMap.put(getActionType(), this);
    }

    public boolean isActionEnum(String xml) {
        return false;
    }

    public Object doAction(String xml) {
        return null;
    }

    public abstract int getActionType();

    public abstract String getActionWord();
}
