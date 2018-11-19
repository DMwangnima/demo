package cn.nuofankj.myblog.controller;

import cn.nuofankj.myblog.constant.FriendTipData;
import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.service.ApiService;
import cn.nuofankj.myblog.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/blogapi/index.php/w")
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
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/getAbout")
    public MessageDto getAbout() {
        try {
            AboutDto aboutDto = apiService.about();
            return MessageDto.valueOf(aboutDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/comments/list")
    public MessageDto comments(String articleId) {
        try {
            CommentsDto comments = apiService.comments(articleId);
            return MessageDto.valueOf(comments, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/article/list")
    public MessageDto articles(@RequestParam(required = false , defaultValue = "")String by, @RequestParam(required = false)String categoryId, @RequestParam(required = false)String tagId, int page, int pageSize) {
        try {
            ArticleInfoDetailDto articles = apiService.articles(by, categoryId, tagId, page, pageSize);
            return MessageDto.valueOf(articles, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/article")
    public MessageDto article(String id) {
        try {
            ArticleDetailDto articleDetailDto = apiService.article(id);
            return MessageDto.valueOf(articleDetailDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping(value = "/comments/add", method = RequestMethod.POST)
    public MessageDto add(HttpServletRequest request, String articleId, @RequestParam(required = false)Long parentId, String name, int replyId, String content, String sourceContent, String ticket, String randstr, String email) {
        try {
            if(parentId == null) {
                parentId = 0L;
            }
            apiService.addComment(CommonUtil.getIpAddr(request), articleId, parentId, name, replyId, content, sourceContent, ticket, randstr, email);
            return MessageDto.valueOf(null, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("category/list")
    public MessageDto categorys() {
        try {
            CategorysDto categorys = apiService.categorys();
            return MessageDto.valueOf(categorys, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("tag/list")
    public MessageDto tags() {
        try {
            TagsDetailDto tags = apiService.tags();
            return MessageDto.valueOf(tags, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("friends/list")
    public MessageDto friends() {
        try {
            FriendsTypeDto[] friends = apiService.friends();
            return MessageDto.valueOf(friends, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("article/search")
    public MessageDto search(String searchValue, int page, int pageSize) {
        try {
            SearchDto search = apiService.search(searchValue, page, pageSize);
            return MessageDto.valueOf(search, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("article/archives")
    public MessageDto archives(int page, int pageSize) {
        try {
            ArchivesDetailDto archives = apiService.archives(page, pageSize);
            return MessageDto.valueOf(archives, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("getResume")
    public MessageDto getResume() {
        try {
            ResumeDto resume = apiService.resume();
            return MessageDto.valueOf(resume, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error("error",e);
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }
}
