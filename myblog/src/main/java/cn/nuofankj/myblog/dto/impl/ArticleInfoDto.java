package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Article;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ArticleInfoDto {
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

    public static ArticleInfoDto toDto(Article article) {
        ArticleInfoDto articleDto = new ArticleInfoDtoConvert().toDTO(article);

        return articleDto;
    }

    private static class ArticleInfoDtoConvert implements DTOConvert<Article, ArticleInfoDto> {

        @Override
        public Article toENT(ArticleInfoDto articleDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public ArticleInfoDto toDTO(Article article) {

            ArticleInfoDto articleDto = new ArticleInfoDto();
            BeanUtils.copyProperties(article, articleDto);
            return articleDto;
        }
    }
}
