package cn.nuofankj.myblog.dto.impl;

import lombok.Data;

import java.util.List;

@Data
public class ArticlesDto {
    private int page;
    private int pageSize;
    private int count;
    private List<ArticleDetailDto> list;

    public static ArticlesDto valueOf(int page, int pageSize, int count, List<ArticleDetailDto> list) {
        ArticlesDto articlesDto = new ArticlesDto();
        articlesDto.setPage(page);
        articlesDto.setPageSize(pageSize);
        articlesDto.setCount(count);
        articlesDto.setList(list);
        return  articlesDto;
    }

}
