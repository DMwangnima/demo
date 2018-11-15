package cn.nuofankj.myblog.service;

import cn.nuofankj.myblog.dto.impl.*;

public interface AdminService {
    AdminUserDto login(String username, String password);

    String saveArticle(String id, String content, String htmlContent, String title, String cover, String subMessage, String isEncrypt);

    StatisticsDto statisticsHome();

    SysListDto sysLog(int page, int pageSize);

    AdminArticleListDto articleList(String by,int status, int page, int pageSize);

    CategoryListDto categoryList(String all);

    TagListDto tagList(String all);

    CommentListDto commentsList(int page, int pageSize);

    FriendListDto friendList(int page, int pageSize);
}
