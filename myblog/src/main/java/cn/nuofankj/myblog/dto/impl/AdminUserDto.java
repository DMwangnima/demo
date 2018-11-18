package cn.nuofankj.myblog.dto.impl;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AdminUserDto {
    private String userId;
    private String userName;
    private String lastLoginTime;
    private Map<String,Object> token = new HashMap<>();

    public static AdminUserDto valueOf(String userId, String userName, long lastLoginTime, String accessToken, long tokenExpiresIn, long exp) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setUserId(userId);
        adminUserDto.setUserName(userName);
        adminUserDto.setLastLoginTime(lastLoginTime + "");
        adminUserDto.token.put("accessToken",accessToken);
        adminUserDto.token.put("tokenExpiresIn",tokenExpiresIn + "");
        adminUserDto.token.put("exp",exp);
        return adminUserDto;
    }
}
