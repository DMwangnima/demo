package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class SysLogDto {
    private long id;
    private long time;
    private String content;
    private String ip;

    public static SysLogDto toDto(SysLog sysLog) {

        SysLogDto sysLogDto = new SysLogDtoConvert().toDTO(sysLog);
        return sysLogDto;
    }

    private static class SysLogDtoConvert implements DTOConvert<SysLog, SysLogDto> {

        @Override
        public SysLog toENT(SysLogDto sysLogDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public SysLogDto toDTO(SysLog sysLog) {

            SysLogDto sysLogDto = new SysLogDto();
            BeanUtils.copyProperties(sysLog, sysLogDto);
            return sysLogDto;
        }
    }
}
