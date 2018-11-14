package cn.nuofankj.myblog.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsDto {
    private long publishCount;
    private long draftsCount;
    private long deletedCount;
    private long categoryCount;
    private long tagCount;
    private long commentsCount;

    public static StatisticsDto valueOf(long publishCount, long draftsCount, long deletedCount, long categoryCount,
                                        long tagCount, long commentsCount){
        StatisticsDto statisticsDto = new StatisticsDto(publishCount,draftsCount,deletedCount,categoryCount,tagCount,commentsCount);
        return statisticsDto;
    }
}
