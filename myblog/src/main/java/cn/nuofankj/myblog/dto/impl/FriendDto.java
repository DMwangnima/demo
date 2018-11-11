package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Friends;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class FriendDto {
    private String friendId;
    private String name;
    private String url;
    private long createTime;
    private long updateTime;
    private long deleteTime;
    private String status;


    public static FriendDto toDTO(Friends friends) {
        FriendDto friendDto = new FriendDtoConvert().toDTO(friends);
        return friendDto;
    }


    private static class FriendDtoConvert implements DTOConvert<Friends, FriendDto> {

        @Override
        public Friends toENT(FriendDto friendDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public FriendDto toDTO(Friends friends) {

            FriendDto friendDto = new FriendDto();
            BeanUtils.copyProperties(friends, friendDto);
            return friendDto;
        }
    }

}
