package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Tag;
import lombok.Data;

import java.util.List;

@Data
public class TagsDetailDto {
    private int count;
    private List<TagDetailDto> list;

    public static TagsDetailDto valueOf(int count, List<Tag> list) {
        TagsDetailDto tagsDetailDto = new TagsDetailDto();
        tagsDetailDto.setCount(count);
        for(Tag tag : list) {
            TagDetailDto tagDetailDto = TagDetailDto.toDto(tag);
            tagsDetailDto.list.add(tagDetailDto);
        }
        return tagsDetailDto;
    }
}
