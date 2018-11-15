package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class AdminArticleListDto {
    private int page;
    private int pageSize;
    private int count;
    private List<AdminArticleDto> list = new ArrayList<>();

    public static AdminArticleListDto valueOf(int page, int pageSize, List<Article> list) {
        AdminArticleListDto adminArticleListDto = new AdminArticleListDto();
        adminArticleListDto.setPage(page).setPageSize(pageSize);
        for(Article article : list) {
            adminArticleListDto.list.add(AdminArticleDto.toDto(article));
        }
        return adminArticleListDto;
    }
}
