package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoryDetailDto {
    private String categoryId;
    private String categoryName;
    private long createTime;
    private long updateTime;
    private int status;
    private int articleCount;

    public static CategoryDetailDto toDto(Category category) {
        CategoryDetailDto categoryDto = new CategoryDetailDtoConvert().toDTO(category);
        return categoryDto;
    }

    private static class CategoryDetailDtoConvert implements DTOConvert<Category, CategoryDetailDto> {

        @Override
        public Category toENT(CategoryDetailDto categoryDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public CategoryDetailDto toDTO(Category category) {
            // TODO 此处的CategoryDetailDto有问题，应该会导致无法转换
            CategoryDetailDto categoryDto = new CategoryDetailDto();
            BeanUtils.copyProperties(category, categoryDto);
            return categoryDto;
        }
    }
}
