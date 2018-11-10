package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.dto.DTOConvert;
import cn.nuofankj.myblog.entity.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class CategoryDto {
    private String id;
    private String name;

    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDtoConvert().toDTO(category);
        return categoryDto;
    }

    private static class CategoryDtoConvert implements DTOConvert<Category, CategoryDto> {

        @Override
        public Category toENT(CategoryDto categoryDto) {

            throw new AssertionError("不支持正向转换方法");
        }

        @Override
        public CategoryDto toDTO(Category category) {

            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(category, categoryDto);
            return categoryDto;
        }
    }
}
