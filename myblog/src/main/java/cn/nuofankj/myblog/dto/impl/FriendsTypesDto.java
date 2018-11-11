package cn.nuofankj.myblog.dto.impl;

import lombok.Data;


@Data
public class FriendsTypesDto {

    private FriendsTypeDto[] dtos;

    public static FriendsTypesDto valueOf(FriendsTypeDto[] dtos) {
        FriendsTypesDto friendsTypesDto = new FriendsTypesDto();
        friendsTypesDto.setDtos(dtos);
        return friendsTypesDto;
    }
}
