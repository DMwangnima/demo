package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Tag;
import org.springframework.beans.BeanUtils;

public class TagDetailDto {
    private String tagId;
    private String tagName;
    private long createTime;
    private long updateTime;
    private int status;
    private int articleCount;

    public static TagDetailDto toDto(Tag tag) {
        TagDetailDto tagDto = new TagDetailDtoConvert().toDTO(tag);
        return tagDto;
    }

    private static class TagDetailDtoConvert implements DTOConvert<Tag, TagDetailDto> {

        @Override
        public Tag toENT(TagDetailDto tagDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public TagDetailDto toDTO(Tag tag) {

            TagDetailDto tagDto = new TagDetailDto();
            BeanUtils.copyProperties(tag, tagDto);
            return tagDto;
        }
    }
}
