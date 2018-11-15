package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Article;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@Data
public class AdminArticleDto {
    private long id;
    private String title;
    private String cover;
    private long pageview;
    private long status;
    private String isEncrypt;
    private String categoryName;
    private long createTime;
    private long deleteTime;
    private long updateTime;
    private long publishTime;

    public static AdminArticleDto toDto(Article article) {

        AdminArticleDto adminArticleDto = new AdminArticleDtoConvert().toDTO(article);
        return adminArticleDto;
    }

    private static class AdminArticleDtoConvert implements DTOConvert<Article, AdminArticleDto> {

        @Override
        public Article toENT(AdminArticleDto adminArticleDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public AdminArticleDto toDTO(Article article) {

            AdminArticleDto adminArticleDto = new AdminArticleDto();
            BeanUtils.copyProperties(article, adminArticleDto);
            return adminArticleDto;
        }
    }
}
