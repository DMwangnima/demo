package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Article;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ArticleMsgDto {
    private String id;
    private String title;
    private String categoryId;
    private long createTime;
    private long deleteTime;
    private long updateTime;
    private long publishTime;
    private long status;
    private String cover;
    private String subMessage;
    private long pageview;
    private String isEncrypt;

    public static ArticleMsgDto toDto(Article article) {
        ArticleMsgDto articleDto = new ArticleMsgDtoConvert().toDTO(article);

        return articleDto;
    }

    private static class ArticleMsgDtoConvert implements DTOConvert<Article, ArticleMsgDto> {

        @Override
        public Article toENT(ArticleMsgDto articleDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public ArticleMsgDto toDTO(Article article) {

            ArticleMsgDto articleDto = new ArticleMsgDto();
            BeanUtils.copyProperties(article, articleDto);
            return articleDto;
        }
    }
}
