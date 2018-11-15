package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CommentListDto {
    private int page;
    private int pageSize;
    private int count;
    private List<CommentBaseDto> list = new ArrayList<>();

    public static CommentListDto valueOf(int page, int pageSize, int count, List<Comments> list) {
        CommentListDto commentListDto = new CommentListDto();
        for(Comments comments : list) {
            commentListDto.list.add(CommentBaseDto.toDto(comments));
        }
        commentListDto.setCount(count).setPage(page).setPageSize(pageSize);
        return commentListDto;
    }
}
