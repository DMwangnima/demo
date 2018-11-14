package cn.nuofankj.myblog.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FriendListDto {
    private int page;
    private int pageSize;
    private int count;
    private List<FriendBaseDto> list;
}
