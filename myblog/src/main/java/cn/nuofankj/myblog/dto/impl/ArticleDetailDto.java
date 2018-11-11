package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Article;
import cn.nuofankj.myblog.entity.Category;
import cn.nuofankj.myblog.entity.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ArticleDetailDto {
    private ArticleDto article;
    private CategoryDto category;
    private List<TagDto> tags = new ArrayList<>();
    private Map<String, String> qrcode = new HashMap<>();
    private Map<String, ArticleDescDto> pn = new HashMap<>();

    public static ArticleDetailDto valueOf(Article article, Category category, List<Tag> tags, Map<String, String> qrcode, Map<String, ArticleDescDto> pn) {
        ArticleDetailDto articleDetailDto = new ArticleDetailDto();
        ArticleDto articleDto = ArticleDto.toDto(article);
        articleDetailDto.setArticle(articleDto);
        CategoryDto categoryDto = CategoryDto.toDto(category);
        articleDetailDto.setCategory(categoryDto);
        for(Tag tag : tags) {
            articleDetailDto.tags.add(TagDto.toDto(tag));
        }
        articleDetailDto.setQrcode(qrcode);
        articleDetailDto.setPn(pn);

        return articleDetailDto;
    }
}
