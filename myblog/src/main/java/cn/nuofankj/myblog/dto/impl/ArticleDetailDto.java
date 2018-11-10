package cn.nuofankj.myblog.dto.impl;

import cn.nuofankj.myblog.entity.Category;
import cn.nuofankj.myblog.entity.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ArticleDetailDto {
    private ArticleDto article;
    private CategoryDto categoryDto;
    private List<TagDto> tags = new ArrayList<>();
    private Map<String, String> qrcode = new HashMap<>();
    private Map<String, ArticleDescDto> pn = new HashMap<>();

    public ArticleDetailDto valueOf(ArticleDto article, Category category, List<Tag> tags, Map<String, String> qrcode,  Map<String, ArticleDescDto> pn) {
        return null;
    }
}
