package cn.nuofankj.myblog.dto.impl;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentsDto {
    private int count;
    private List<CommentDto> list = new ArrayList<>();

    public static CommentsDto valueOf(List<CommentDto> list, int count) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(count);
        commentsDto.setList(list);
        return commentsDto;
    }
}
