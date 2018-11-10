package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Tag;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class TagDto {
    private String id;
    private String name;

    public static TagDto toDto(Tag tag) {
        TagDto tagDto = new TagDtoConvert().toDTO(tag);
        return tagDto;
    }

    private static class TagDtoConvert implements DTOConvert<Tag, TagDto> {

        @Override
        public Tag toENT(TagDto tagDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public TagDto toDTO(Tag tag) {

            TagDto tagDto = new TagDto();
            BeanUtils.copyProperties(tag, tagDto);
            return tagDto;
        }
    }
}
