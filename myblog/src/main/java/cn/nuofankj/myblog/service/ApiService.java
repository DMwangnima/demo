package cn.nuofankj.myblog.service;

import cn.nuofankj.myblog.dto.impl.*;

public interface ApiService {

    BlogConfigDto blogInfo();

    AboutDto about();

    CommentsDto comments(String articleId);

    ArticleInfoDetailDto articles(String by, String categoryId, String tagId, int page, int pageSize);

    ArticleDetailDto article(String id);

    void addComment(String ip,String articleId, long parentId, String name, int replyId, String content, String sourceContent, String ticket, String randstr, String email);

    CategorysDto categorys();

    TagsDetailDto tags();

    FriendsTypeDto[] friends();

    SearchDto search(String searchValue, int page, int pageSize);

    ArchivesDetailDto archives(int page, int pageSize);

    ResumeDto resume();
}
