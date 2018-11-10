package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Comments;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentsDto {
    private int count;
    private List<CommentDto> list = new ArrayList<>();

    public static CommentsDto valueOf(List<Comments> list, int count) {
        CommentsDto commentsDto = new CommentsDto();
        commentsDto.setCount(count);
        for(Comments comment : list) {
            CommentDto commentDto = CommentDto.toDto(comment);
            commentsDto.list.add(commentDto);
        }
        return commentsDto;
    }
}
