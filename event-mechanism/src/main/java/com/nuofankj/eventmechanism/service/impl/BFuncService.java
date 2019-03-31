package com.nuofankj.eventmechanism.service.impl;

import com.nuofankj.eventmechanism.anno.ReceiveAnno;
import com.nuofankj.eventmechanism.event.LoginEvent;
import com.nuofankj.eventmechanism.service.IBFuncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xifanxiaxue
 * @date 3/31/19
 * @desc
 */
@Service
@Slf4j
public class BFuncService implements IBFuncService {

  @ReceiveAnno(clz = LoginEvent.class)
  private void doAfterLogin() {
    log.info("[{}]监听到登录事件 ... ", this.getClass());
  }
}
