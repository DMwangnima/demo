package cn.nuofankj.myblog.controller;

import cn.nuofankj.myblog.constant.FriendTipData;
import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogapi/index.php/a")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public MessageDto login(String username, String password) {
        try {
            AdminUserDto login = adminService.login(username, password);
            return MessageDto.valueOf(login, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }


    @RequestMapping("/error")
    public MessageDto error(String username, String password) {
        try {
            return MessageDto.valueOf("验证身份失败", FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/statistics/home")
    public MessageDto statisticsHome() {
        try {
            StatisticsDto statisticsDto = adminService.statisticsHome();
            return MessageDto.valueOf(statisticsDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/sys/log")
    public MessageDto sysLog(int page, int pageSize) {
        try {
            SysListDto sysListDto = adminService.sysLog(page, pageSize);
            return MessageDto.valueOf(sysListDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/article/list")
    public MessageDto articleList(String by, int status, int page, int pageSize) {
        try {
            AdminArticleListDto adminArticleListDto = adminService.articleList(by, status, page, pageSize);
            return MessageDto.valueOf(adminArticleListDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/comments/alllist")
    public MessageDto commentsList(int page, int pageSize) {
        try {
            CommentListDto commentListDto = adminService.commentsList(page, pageSize);
            return MessageDto.valueOf(commentListDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/article/save")
    public MessageDto commentsList(String id, String content, String htmlContent, String title, String cover, String subMessage, String isEncrypt) {
        try {
            String articleId = adminService.saveArticle(id, content, htmlContent, title, cover, subMessage, isEncrypt);
            return MessageDto.valueOf(articleId, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }


    // TODO 最后两个参数我实在有点日狗了，完全不知道什么格式
    @RequestMapping("/article/publish")
    public MessageDto publish(String id, String content, String htmlContent, String title, String cover, String subMessage, String isEncrypt, @RequestParam(value = "category[]") List<String> category, @RequestParam(value = "tags[][]") List<String> tags) {
        try {
            String articleId = adminService.publish(id, content, htmlContent, title, cover, subMessage, isEncrypt, category.get(0),tags.get(0));
            return MessageDto.valueOf(articleId, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/category/list")
    public MessageDto categoryList(String all) {
        try {
            CategoryListDto categoryListDto = adminService.categoryList(all);
            return MessageDto.valueOf(categoryListDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/tag/list")
    public MessageDto tagList(String all) {
        try {
            TagListDto tagListDto = adminService.tagList(all);
            return MessageDto.valueOf(tagListDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/qiniu/token")
    public MessageDto qiniuToken(String bucket, String withWater) {
        try {
            String token = adminService.qiniuToken(bucket, withWater);
            return MessageDto.valueOf(token, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }
}
