package cn.nuofankj.myblog.dto.impl;

import lombok.Data;

import java.util.List;

@Data
public class ArticleInfoDetailDto {
    private int page;
    private int pageSize;
    private int count;
    private List<ArticleMsgDetailDto> list;

    public static ArticleInfoDetailDto valueOf(int page, int pageSize, int count, List<ArticleMsgDetailDto> list) {
        ArticleInfoDetailDto articlesDto = new ArticleInfoDetailDto();
        articlesDto.setPage(page);
        articlesDto.setPageSize(pageSize);
        articlesDto.setCount(count);
        articlesDto.setList(list);
        return  articlesDto;
    }
}
