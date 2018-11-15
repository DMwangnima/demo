package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Friends;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FriendListDto {
    private int page;
    private int pageSize;
    private int count;
    private List<FriendBaseDto> list = new ArrayList<>();

    public static FriendListDto valueOf(int page, int pageSize, int count, List<Friends> list) {
        FriendListDto friendListDto = new FriendListDto();
        for(Friends friends : list) {
            friendListDto.list.add(FriendBaseDto.toDto(friends));
        }
        friendListDto.setCount(count).setPage(page).setPageSize(pageSize);
        return friendListDto;
    }
}
