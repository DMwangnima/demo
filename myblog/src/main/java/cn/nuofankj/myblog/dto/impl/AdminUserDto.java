package cn.nuofankj.myblog.dto.impl;

import lombok.Data;

import java.util.Map;

@Data
public class AdminUserDto {
    private String userId;
    private String userName;
    private String lastLoginTime;
    private Map<String,String> token;

    public AdminUserDto valueOf(String userId, String userName, String lastLoginTime, String accessToken, String tokenExpiresIn, String exp) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setUserId(userId);
        adminUserDto.setLastLoginTime(lastLoginTime);
        adminUserDto.token.put("accessToken",accessToken);
        adminUserDto.token.put("tokenExpiresIn",tokenExpiresIn);
        adminUserDto.token.put("exp",exp);
        return adminUserDto;
    }
}
