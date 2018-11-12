package cn.nuofankj.myblog.dto.impl;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ArchivesDetailDto {
    private int page;
    private int pageSize;
    private int count;
    private Map<String, Map<String, List<ArticleDetailDto>>> list;

    public static ArchivesDetailDto valueOf(int page, int pageSize, int count, Map<String, Map<String, List<ArticleDetailDto>>> list) {
        ArchivesDetailDto archivesDetailDto = new ArchivesDetailDto();
        archivesDetailDto.setPage(page);
        archivesDetailDto.setPageSize(pageSize);
        archivesDetailDto.setCount(count);
        archivesDetailDto.setList(list);
        return archivesDetailDto;
    }
}
