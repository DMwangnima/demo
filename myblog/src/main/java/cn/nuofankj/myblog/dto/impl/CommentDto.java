package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Comments;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CommentDto {
    private long id;
    private String articleId;
    private long parentId;
    private long replyId;
    private String name;
    private String email;
    private String content;
    private long createTime;
    private long status;
    private String isAuthor;

    public static CommentDto toDto(Comments comments) {
        CommentDto commentDto = new CommentDtoConvert().toDTO(comments);
        return commentDto;
    }

    private static class CommentDtoConvert implements DTOConvert<Comments, CommentDto> {

        @Override
        public Comments toENT(CommentDto commentDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public CommentDto toDTO(Comments comments) {

            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comments, commentDto);
            return commentDto;
        }
    }
}
