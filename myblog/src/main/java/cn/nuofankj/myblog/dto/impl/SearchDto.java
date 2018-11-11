package cn.nuofankj.myblog.dto.impl;

import lombok.Data;

import java.util.List;

@Data
public class SearchDto {
    private int page;
    private int pageSize;
    private int count;
    private List<ArticleDetailDto> list;

    public static SearchDto valueOf(int page, int pageSize, int count, List<ArticleDetailDto> list) {
        SearchDto searchDto = new SearchDto();
        searchDto.setPage(page);
        searchDto.setPage(page);
        searchDto.setCount(count);
        searchDto.setList(list);
        return searchDto;
    }
}
