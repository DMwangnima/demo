package cn.nuofankj.myblog.service.impl;

import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.entity.*;
import cn.nuofankj.myblog.repository.*;
import cn.nuofankj.myblog.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Configuration
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Autowired
    private BlogConfigRepository blogConfigRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private PagesRepository pagesRepository;
    @Autowired
    private CommentsRepository commentsRepository;

    @Override
    public BlogConfigDto blogInfo() {

        List<BlogConfig> all = blogConfigRepository.findAll();
        if(all.size() == 0) {
            log.error("BlogConfig为空，要报错啦！！！");
            return null;
        }
        // TODO 暫時取第一個
        BlogConfig blogConfig = all.get(0);
        long categoryCount = categoryRepository.count();
        long tagCount = tagRepository.count();
        long articleCount = articleRepository.count();
        BlogConfigDto blogConfigDto = BlogConfigDto.toBlogInfo(blogConfig, categoryCount, tagCount, articleCount);
        return blogConfigDto;
    }

    @Override
    public AboutDto getAbout() {

        List<BlogConfig> configs = blogConfigRepository.findAll();
        if(configs.size() == 0) {
            log.error("BlogConfig为空，要报错啦！！！");
            return null;
        }
        // TODO 暫時取第一個
        BlogConfig blogConfig = configs.get(0);
        Map<String, String> qrcode = new HashMap<>();
        qrcode.put("wxpayQrcode", blogConfig.getWxpayQrcode());
        qrcode.put("alipayQrcode", blogConfig.getAlipayQrcode());
        List<Pages> pages = pagesRepository.findAll();
        if(pages.size() == 0) {
            log.error("Pages为空，要报错啦！！！");
            return null;
        }
        // TODO 暫時取第一個
        String html = pages.get(0).getHtml();
        AboutDto aboutDto = AboutDto.valueOf(qrcode, html);
        return aboutDto;
    }

    @Override
    public CommentsDto comments(long articleId) {
        List<Comments> comments = commentsRepository.findAll();
        CommentsDto commentsDto = CommentsDto.valueOf(comments, comments.size());
        return commentsDto;
    }

    @Override
    public ArticlesDto articles(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "aid");
        Page<Article> all = articleRepository.findAll(pageable);
        List<Article> articles = all.getContent();
        ArticlesDto articlesDto = ArticlesDto.valueOf(page, pageSize, articles.size(), articles);
        return articlesDto;
    }

    @Override
    public ArticleDetailDto article(String id) {

        List<BlogConfig> configs = blogConfigRepository.findAll();
        if(configs.size() == 0) {
            log.error("BlogConfig为空，要报错啦！！！");
            return null;
        }
        // TODO 暫時取第一個
        BlogConfig blogConfig = configs.get(0);
        Map<String, String> qrcode = new HashMap<>();
        qrcode.put("wxpayQrcode", blogConfig.getWxpayQrcode());
        qrcode.put("alipayQrcode", blogConfig.getAlipayQrcode());

        Article article = articleRepository.findArticleById(id);

        Category category = categoryRepository.findCategoryById(article.getId());

        Optional<Tag> tagOptional = tagRepository.findById(category.getAid());
        Tag tag = tagOptional.get();

        Map<String, ArticleDescDto> pn = new HashMap<>();
        
        return articleDto;
    }
}
