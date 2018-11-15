package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.SysLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class SysListDto {
    private int page;
    private int pageSize;
    private int count;
    private List<SysLogDto> list = new ArrayList<>();

    public static SysListDto valueOf(int page, int pageSize, List<SysLog> list) {
        SysListDto sysListDto = new SysListDto();
        sysListDto.setPage(page).setPageSize(pageSize);
        for(SysLog sysLog : list) {
            sysListDto.list.add(SysLogDto.toDto(sysLog));
        }
        return sysListDto;
    }
}
