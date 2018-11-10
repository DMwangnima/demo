package cn.nuofankj.myblog.service;

import cn.nuofankj.myblog.dto.impl.*;

public interface ApiService {

    BlogConfigDto blogInfo();

    AboutDto getAbout();

    CommentsDto comments(long articleId);

    ArticlesDto articles(int page, int pageSize);

    ArticleDetailDto article(String id);
}
