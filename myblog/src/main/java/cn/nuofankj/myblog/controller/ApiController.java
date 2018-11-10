package cn.nuofankj.myblog.controller;

import cn.nuofankj.myblog.constant.FriendTipData;
import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @Autowired
    private ApiService apiService;

    @RequestMapping("/blogInfo")
    public MessageDto blogInfo() {
        try {
            BlogConfigDto blogConfigDto = apiService.blogInfo();
            return MessageDto.valueOf(blogConfigDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/getAbout")
    public MessageDto getAbout() {
        try {
            AboutDto aboutDto = apiService.getAbout();
            return MessageDto.valueOf(aboutDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/comments")
    public MessageDto comments(int articleId) {
        try {
            AboutDto aboutDto = apiService.getAbout();
            return MessageDto.valueOf(aboutDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/article/list")
    public MessageDto articles(int page, int pageSize) {
        try {
            ArticlesDto articles = apiService.articles(page, pageSize);
            return MessageDto.valueOf(articles, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/article")
    public MessageDto article(String id) {
        try {
            ArticleDto articles = apiService.article(id);
            return MessageDto.valueOf(articles, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }
}
