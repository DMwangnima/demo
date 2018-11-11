package cn.nuofankj.myblog.controller;

import cn.nuofankj.myblog.constant.FriendTipData;
import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping("/comments/list")
    public MessageDto comments(int articleId) {
        try {
            AboutDto aboutDto = apiService.getAbout();
            return MessageDto.valueOf(aboutDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/article/list")
    public MessageDto articles(@RequestParam(required = false)String by, @RequestParam(required = false)String categoryId, @RequestParam(required = false)String tagId, int page, int pageSize) {
        try {
            ArticlesDto articles = apiService.articles(by, categoryId, tagId, page, pageSize);
            return MessageDto.valueOf(articles, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            log.error(e.getMessage());
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/article")
    public MessageDto article(String id) {
        try {
            ArticleDetailDto articleDetailDto = apiService.article(id);
            return MessageDto.valueOf(articleDetailDto, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("/comments/add")
    public MessageDto add(String articleId, @RequestParam(required = false)long parentId, String name, int replyId, String content, String sourceContent, String ticket, String randstr, String email) {
        try {
            apiService.addComment(articleId, parentId, name, replyId, content, sourceContent, ticket, randstr, email);
            return MessageDto.valueOf(null, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("category/list")
    public MessageDto categorys() {
        try {
            CategorysDto categorys = apiService.categorys();
            return MessageDto.valueOf(categorys, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("tag/list")
    public MessageDto tags() {
        try {
            TagsDetailDto tags = apiService.tags();
            return MessageDto.valueOf(tags, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("friends/list")
    public MessageDto friends() {
        try {
            FriendsTypesDto friends = apiService.friends();
            return MessageDto.valueOf(friends, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }

    @RequestMapping("article/search")
    public MessageDto search(String searchValue, int page, int pageSize) {
        try {
            SearchDto search = apiService.search(searchValue, page, pageSize);
            return MessageDto.valueOf(search, FriendTipData.SUCCESS_CODE, FriendTipData.SUCCESS_MSG, true);
        } catch (Exception e) {
            return MessageDto.valueOf(null, FriendTipData.ERROR_CODE, FriendTipData.ERROR_MSG, false);
        }
    }
}
