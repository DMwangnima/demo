package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.BlogConfig;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class BlogConfigDto {

    private String blogName;
    private String avatar;
    private String sign;
    private String wxpayQrcode;
    private String alipayQrcode;
    private String github;
    private String salt;
    private long articleCount;
    private long categoryCount;
    private long tagCount;

    public static BlogConfigDto toDTO(BlogConfig blogConfig, long categoryCount, long tagCount, long articleCount) {
        BlogConfigDto blogConfigDto = new BlogConfigDtoConvert().toDTO(blogConfig);
        blogConfigDto.setArticleCount(articleCount);
        blogConfigDto.setCategoryCount(categoryCount);
        blogConfigDto.setTagCount(tagCount);
        return blogConfigDto;
    }


    private static class BlogConfigDtoConvert implements DTOConvert<BlogConfig, BlogConfigDto> {

        @Override
        public BlogConfig toENT(BlogConfigDto blogConfigDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public BlogConfigDto toDTO(BlogConfig blogConfig) {

            BlogConfigDto blogConfigDto = new BlogConfigDto();
            BeanUtils.copyProperties(blogConfig, blogConfigDto);
            return blogConfigDto;
        }
    }

}
