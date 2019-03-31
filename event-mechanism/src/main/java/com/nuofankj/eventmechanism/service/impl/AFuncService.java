package com.nuofankj.eventmechanism.service.impl;

import com.nuofankj.eventmechanism.event.LoginEvent;
import com.nuofankj.eventmechanism.service.IAFuncService;
import com.nuofankj.eventmechanism.support.EventContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xifanxiaxue
 * @date 3/31/19
 * @desc
 */
@Slf4j
@Service
public class AFuncService implements IAFuncService {

  @Override
  public void login() {
    log.info("[{}]抛出登录事件 ... ", this.getClass());
    EventContainer.submit(LoginEvent.class);
  }
}
