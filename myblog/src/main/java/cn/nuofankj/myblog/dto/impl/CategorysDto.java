package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategorysDto {
    private int count;

    private List<CategoryDetailDto> list = new ArrayList<>();

    public static CategorysDto valueOf(int count, List<Category> categories) {
        CategorysDto categorysDto = new CategorysDto();
        categorysDto.setCount(count);
        for(Category category : categories) {
            CategoryDetailDto categoryDetailDto = CategoryDetailDto.toDto(category);
            categorysDto.list.add(categoryDetailDto);
        }
        return categorysDto;
    }
}
