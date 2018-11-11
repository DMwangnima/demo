package cn.nuofankj.myblog.service;

import cn.nuofankj.myblog.dto.impl.*;

public interface ApiService {

    BlogConfigDto blogInfo();

    AboutDto getAbout();

    CommentsDto comments(long articleId);

    ArticlesDto articles(String by, String categoryId, String tagId, int page, int pageSize);

    ArticleDetailDto article(String id);

    void addComment(String articleId, long parentId, String name, int replyId, String content, String sourceContent, String ticket, String randstr, String email);

    CategorysDto categorys();

    TagsDetailDto tags();

    FriendsTypesDto friends();

    SearchDto search(String searchValue, int page, int pageSize);
}
