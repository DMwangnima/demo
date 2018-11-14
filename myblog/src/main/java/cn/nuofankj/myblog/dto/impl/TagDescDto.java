package cn.nuofankj.myblog.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagDescDto {
    private String tagId;
    private String tagName;
    private long createTime;
    private long updateTime;
    private int status;
    private int articleCount;
}
