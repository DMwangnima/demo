package cn.nuofankj.myblog.service;

import cn.nuofankj.myblog.dto.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    MessageDto login(String username, String password, HttpSession session, String ip);

    String saveArticle(String id, String content, String htmlContent, String title, String cover, String subMessage, int isEncrypt, String category, String tags);

    StatisticsDto statisticsHome();

    SysListDto sysLog(int page, int pageSize);

    AdminArticleListDto articleList(String by,int status, int page, int pageSize);

    CategoryListDto categoryList(String all);

    TagListDto tagList(String all);

    CommentsDto commentsList(int page, int pageSize);

    CommentsDto commentsListByArticleId(String articleId);

    FriendListDto friendList(int page, int pageSize);

    String publish(String ip, HttpServletRequest request, String id, String content, String htmlContent, String title, String cover, String subMessage, int isEncrypt, String category, String tags);

    Map<String,String> qiniuToken(String bucket, String withWater, HttpServletRequest request);

    ArticleDetailDto articleInfo(String id);

    String modifyArticle(String title, String cover, String subMessage, int isEncrypt, String content, String htmlContent, String id, String category, String tags);

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

    FriendsTypeDto[] friendTypeList();
}
