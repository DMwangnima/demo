package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Comments;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class CommentDto {
    private long id;
    private String articleId;
    private long parentId;
    private long replyId;
    private String name;
    private String email;
    private String content;
    private String sourceContent;
    private long createTime;
    private long deleteTime;
    private long status;
    private int isAuthor;

    public static CommentDto valueOf(String articleId, long parentId, long replyId, String name, String email,
                                     String content, String sourceContent, int isAuthor) {
        CommentDto commentDto = new CommentDto();
        commentDto.setArticleId(articleId);
        commentDto.setParentId(parentId);
        commentDto.setReplyId(replyId);
        commentDto.setName(name);
        commentDto.setEmail(email);
        commentDto.setContent(content);
        commentDto.setSourceContent(sourceContent);
        commentDto.setCreateTime(new Date().getTime());
        commentDto.setIsAuthor(isAuthor);
        return commentDto;
    }

    public static Comments toEnt(CommentDto commentDto) {
        Comments comments = new CommentDtoConvert().toENT(commentDto);
        return comments;
    }

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
