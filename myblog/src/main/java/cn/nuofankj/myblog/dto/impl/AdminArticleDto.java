package cn.nuofankj.myblog.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AdminArticleDto {
    private long id;
    private String title;
    private String cover;
    private long pageview;
    private long status;
    private String isEncrypt;
    private String categoryName;
    private long createTime;
    private long deleteTime;
    private long updateTime;
    private long publishTime;
}
