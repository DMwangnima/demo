package cn.nuofankj.myblog.controller;

import cn.nuofankj.myblog.constant.FriendTipData;
import cn.nuofankj.myblog.dto.impl.BlogConfigDto;
import cn.nuofankj.myblog.dto.impl.MessageDto;
import cn.nuofankj.myblog.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")

    public MessageDto login(String username, String password) {
        try {

            return MessageDto.valueOf(null, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }


}
