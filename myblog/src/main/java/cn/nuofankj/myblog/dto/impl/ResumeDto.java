package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Category;
import cn.nuofankj.myblog.entity.Pages;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ResumeDto {
    private String html;

    public static ResumeDto toDto(Pages pages) {

        ResumeDto resumeDto = new ResumeDtoConvert().toDTO(pages);
        return resumeDto;
    }

    private static class ResumeDtoConvert implements DTOConvert<Pages, ResumeDto> {

        @Override
        public Pages toENT(ResumeDto resumeDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public ResumeDto toDTO(Pages pages) {

            ResumeDto resumeDto = new ResumeDto();
            BeanUtils.copyProperties(pages, resumeDto);
            return resumeDto;
        }
    }
}
