package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Friends;
import cn.nuofankj.myblog.entity.FriendsType;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class FriendsTypeDto {
    private long id;
    private String name;
    private long count;
    private List<FriendDto> list = new ArrayList<>();

    public static FriendsTypeDto toDTO(FriendsType friendsType, List<Friends> list) {
        FriendsTypeDto friendsTypeDto = new FriendsTypeDtoConvert().toDTO(friendsType);
        for(Friends friends : list) {
            FriendDto friendDto = FriendDto.toDTO(friends);
            friendsTypeDto.list.add(friendDto);
        }
        return friendsTypeDto;
    }


    private static class FriendsTypeDtoConvert implements DTOConvert<FriendsType, FriendsTypeDto> {

        @Override
        public FriendsType toENT(FriendsTypeDto friendsTypeDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public FriendsTypeDto toDTO(FriendsType friendsType) {

            FriendsTypeDto friendsTypeDto = new FriendsTypeDto();
            BeanUtils.copyProperties(friendsType, friendsTypeDto);
            return friendsTypeDto;
        }
    }

}
