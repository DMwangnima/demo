package cn.nuofankj.myblog.service;

import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.pojo.TagsPojo;

import javax.servlet.http.HttpSession;

public interface AdminService {
    AdminUserDto login(String username, String password, HttpSession session, String ip);

    String saveArticle(String id, String content, String htmlContent, String title, String cover, String subMessage, String isEncrypt);

    StatisticsDto statisticsHome();

    SysListDto sysLog(int page, int pageSize);

    AdminArticleListDto articleList(String by,int status, int page, int pageSize);

    CategoryListDto categoryList(String all);

    TagListDto tagList(String all);

    CommentListDto commentsList(int page, int pageSize);

    FriendListDto friendList(int page, int pageSize);

    String publish(String id, String content, String htmlContent, String title, String cover, String subMessage, String isEncrypt, String category, String tags);

    String qiniuToken(String bucket, String withWater, HttpSession session);

    ArticleDetailDto articleInfo(String id);

    String modifyArticle(String title, String cover, String subMessage, String isEncrypt, String content, String htmlContent, String id, String categoryId, TagsPojo[] tags);

    void articleDelete(String id);

    String addCategory(String categoryName);

    void modifyCategory(String categoryId, String categoryName);

    void deleteCategory(String categoryId);

    String addTag(String tagName);

    void deleteTag(String tagId);

    void modifyTag(String tagId, String tagName);

    void deleteComments(long id);

    void replyComments(String articleId, long replyId, String content, String sourceContent);

    BlogConfigDto webConfig();

    PageMDDto getAbout();

    PageMDDto getResume();

    void modifyFriends(String name, String url, int type, String friendIdfriendId);

    void deleteFriends(String friendId);
}
