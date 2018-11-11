package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Article;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ArticleDto {
    private String id;
    private String title;
    private String categoryId;
    private long createTime;
    private long deleteTime;
    private long updateTime;
    private long publishTime;
    private long status;
    private String content;
    private String htmlContent;
    private String cover;
    private String subMessage;
    private long pageview;
    private String isEncrypt;

    public static ArticleDto toDto(Article article) {
        ArticleDto articleDto = new ArticleDto.ArticleDtoConvert().toDTO(article);

        return articleDto;
    }

    private static class ArticleDtoConvert implements DTOConvert<Article, ArticleDto> {

        @Override
        public Article toENT(ArticleDto articleDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public ArticleDto toDTO(Article article) {

            ArticleDto articleDto = new ArticleDto();
            BeanUtils.copyProperties(article, articleDto);
            return articleDto;
        }
    }
}
