package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Tag;
import lombok.Data;

@Data
public class TagDetailDto {
    private String tagId;
    private String tagName;
    private long createTime;
    private long updateTime;
    private int status;
    private long articleCount;

    public static TagDetailDto valueOf(Tag tag) {
        TagDetailDto tagDto = new TagDetailDto();
        tagDto.setArticleCount(tag.getArticleCount());
        tagDto.setCreateTime(tag.getCreateTime());
        tagDto.setStatus(tag.getStatus());
        tagDto.setTagId(tag.getId());
        tagDto.setTagName(tag.getName());
        tagDto.setUpdateTime(tag.getUpdateTime());
        return tagDto;
    }
}
