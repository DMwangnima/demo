package cn.nuofankj.myblog.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SysLogDto {
    private long id;
    private long time;
    private String content;
    private String ip;
}
