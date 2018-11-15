package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Comments;
import cn.nuofankj.myblog.entity.Pages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class CommentBaseDto {
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

    public static CommentBaseDto toDto(Comments comments) {

        CommentBaseDto commentBaseDto = new CommentBaseDtoConvert().toDTO(comments);
        return commentBaseDto;
    }

    private static class CommentBaseDtoConvert implements DTOConvert<Comments, CommentBaseDto> {

        @Override
        public Comments toENT(CommentBaseDto commentBaseDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public CommentBaseDto toDTO(Comments comments) {

            CommentBaseDto commentBaseDto = new CommentBaseDto();
            BeanUtils.copyProperties(comments, commentBaseDto);
            return commentBaseDto;
        }
    }
}
