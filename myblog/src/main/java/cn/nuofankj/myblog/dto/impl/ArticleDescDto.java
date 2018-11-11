package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Article;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ArticleDescDto {
    private String id;
    private String title;

    public static ArticleDescDto toDTO(Article article) {
        ArticleDescDtoConvert articleDescDtoConvert = new ArticleDescDtoConvert();
        ArticleDescDto articleDescDto = articleDescDtoConvert.toDTO(article);
        return articleDescDto;
    }

    private static class ArticleDescDtoConvert implements DTOConvert<Article, ArticleDescDto> {

        @Override
        public Article toENT(ArticleDescDto articleDescDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public ArticleDescDto toDTO(Article article) {

            ArticleDescDto articleDescDto = new ArticleDescDto();
            BeanUtils.copyProperties(article, articleDescDto);
            return articleDescDto;
        }
    }

}
