package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Friends;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class FriendBaseDto {
    private long aid;
    private String friendId;
    private String name;
    private String url;
    private long createTime;
    private long updateTime;
    private long deleteTime;
    private String status;
    private long typeId;

    public static FriendBaseDto toDto(Friends friends) {

        FriendBaseDto friendBaseDto = new FriendBaseDtoConvert().toDTO(friends);
        return friendBaseDto;
    }

    private static class FriendBaseDtoConvert implements DTOConvert<Friends, FriendBaseDto> {

        @Override
        public Friends toENT(FriendBaseDto FriendBaseDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public FriendBaseDto toDTO(Friends friends) {

            FriendBaseDto friendBaseDto = new FriendBaseDto();
            BeanUtils.copyProperties(friends, friendBaseDto);
            return friendBaseDto;
        }
    }
}
