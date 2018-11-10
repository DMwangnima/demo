package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Article;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticlesDto {
    private int page;
    private int pageSize;
    private int count;
    private List<ArticleDto> list = new ArrayList<>();

    public static ArticlesDto valueOf(int page, int pageSize, int count, List<Article> list) {
        ArticlesDto articlesDto = new ArticlesDto();
        articlesDto.setPage(page);
        articlesDto.setPageSize(pageSize);
        articlesDto.setCount(count);
        for(Article article : list) {
            articlesDto.list.add(ArticleDto.toArticleDto(article));
        }
        return  articlesDto;
    }

}
