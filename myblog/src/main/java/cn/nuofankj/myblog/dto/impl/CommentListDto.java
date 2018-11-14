package cn.nuofankj.myblog.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentListDto {
    private int page;
    private int pageSize;
    private int count;
    private List<CommentBaseDto> list;
}
