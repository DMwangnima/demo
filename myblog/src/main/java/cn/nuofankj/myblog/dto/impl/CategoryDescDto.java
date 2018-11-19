package cn.nuofankj.myblog.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDescDto {
    private String categoryId;
    private String categoryName;
    private long createTime;
    private long updateTime;
    private int status;
    private long articleCount;
    private int canDel;
}
