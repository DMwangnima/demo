package cn.nuofankj.myblog.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
}
