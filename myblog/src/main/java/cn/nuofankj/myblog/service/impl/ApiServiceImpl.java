package cn.nuofankj.myblog.service.impl;

import cn.nuofankj.myblog.constant.ArticleStatusEnum;
import cn.nuofankj.myblog.constant.PagesTypeEnum;
import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.entity.*;
import cn.nuofankj.myblog.repository.*;
import cn.nuofankj.myblog.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private ArticleTagMapperRepository articleTagMapperRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private FriendsTypeRepository friendsTypeRepository;

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
        BlogConfigDto blogConfigDto = BlogConfigDto.toDTO(blogConfig, categoryCount, tagCount, articleCount);
        return blogConfigDto;
    }

    @Override
    public AboutDto about() {

        List<BlogConfig> configs = blogConfigRepository.findAll();
        if(configs.size() == 0) {
            log.error("BlogConfig为空，要报错啦！！！");
            return null;
        }
        BlogConfig blogConfig = configs.get(0);
        Map<String, String> qrcode = new HashMap<>();
        qrcode.put("wxpayQrcode", blogConfig.getWxpayQrcode());
        qrcode.put("alipayQrcode", blogConfig.getAlipayQrcode());
        Pages page = pagesRepository.findPagesByType(PagesTypeEnum.ABOUT.getStatusMsg());
        String html = page.getHtml();
        AboutDto aboutDto = AboutDto.valueOf(qrcode, html);
        return aboutDto;
    }

    @Override
    public CommentsDto comments(String articleId) {
        // TODO 此处暂定封顶100
        Pageable pageable = PageRequest.of(0, 100, Sort.Direction.DESC, "id");
        List<Comments> commentsList = commentsRepository.findAllByParentIdAndArticleId(0, articleId, pageable);
        List<CommentDto> list = new ArrayList<>();
        for(Comments comments : commentsList) {
            CommentDto commentDto = CommentDto.toDto(comments);
            List<Comments> allByParentId = commentsRepository.findAllByParentId(comments.getId(), pageable);
            List<CommentDto> listTmp = new ArrayList<>();
            for(Comments c : allByParentId) {
                listTmp.add(CommentDto.toDto(c));
            }
            commentDto.setChildren(listTmp);
            list.add(commentDto);
        }
        CommentsDto commentsDto = CommentsDto.valueOf(list, list.size());
        return commentsDto;
    }

    @Override
    public ArticlesDto articles(String by, String categoryId, String tagId, int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "publishTime");
        List<Article> articles = articleRepository.findAllByStatus(ArticleStatusEnum.NORMAL.getStatus(), pageable);
        List<ArticleDetailDto> articleDetailDtos = new ArrayList<>();
        for(Article article : articles) {
            Category category = categoryRepository.findCategoryById(article.getCategoryId());

            List<Tag> tags = new ArrayList<>();

            List<ArticleTagMapper> articleTagMappers = articleTagMapperRepository.findAllByArticleId(article.getId());
            for(ArticleTagMapper articleTagMapper : articleTagMappers) {
                Tag tag = tagRepository.findTagById(articleTagMapper.getTagId());
                tags.add(tag);
            }
            ArticleDetailDto articleDetailDto = ArticleDetailDto.valueOf(article, category, tags, null, null);
            articleDetailDtos.add(articleDetailDto);
        }
        ArticlesDto articlesDto = ArticlesDto.valueOf(page, pageSize, articles.size(), articleDetailDtos);
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

        Category category = categoryRepository.findCategoryById(article.getCategoryId());

        List<ArticleTagMapper> articleTagMappers = articleTagMapperRepository.findAllByArticleId(article.getId());

        List<Tag> tags = new ArrayList<>();

        for(ArticleTagMapper articleTagMapper : articleTagMappers) {
            Tag tag = tagRepository.findTagById(articleTagMapper.getTagId());
            tags.add(tag);
        }

        Map<String, ArticleDescDto> pn = new HashMap<>();
        Pageable pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "aid");
        List<Article> byAidLessThan = articleRepository.findByAidLessThanAndStatus(article.getAid(), ArticleStatusEnum.NORMAL.getStatus(), pageable);
        List<Article> byAidGreaterThan = articleRepository.findByAidGreaterThanAndStatus(article.getAid(), ArticleStatusEnum.NORMAL.getStatus(), pageable);
        if(byAidLessThan.size() > 0) {
            ArticleDescDto articleDescDto = ArticleDescDto.toDTO(byAidLessThan.get(0));
            pn.put("pre", articleDescDto);
        }
        if(byAidGreaterThan.size() > 0) {
            ArticleDescDto articleDescDto = ArticleDescDto.toDTO(byAidGreaterThan.get(0));
            pn.put("next", articleDescDto);
        }

        ArticleDetailDto articleDetailDto = ArticleDetailDto.valueOf(article, category, tags, qrcode, pn);

        return articleDetailDto;
    }

    @Override
    public void addComment(String articleId, long parentId, String name, int replyId, String content, String sourceContent, String ticket, String randstr, String email) {

        CommentDto commentDto = CommentDto.valueOf(articleId, parentId, replyId, name, email, content, sourceContent, 1);
        Comments comments = CommentDto.toEnt(commentDto);
        commentsRepository.save(comments);
    }

    @Override
    public CategorysDto categorys() {
        List<Category> categories = categoryRepository.findAll();
        CategorysDto categorysDto = CategorysDto.valueOf(categories.size(), categories);
        return categorysDto;
    }

    @Override
    public TagsDetailDto tags() {
        List<Tag> tags = tagRepository.findAll();
        TagsDetailDto tagsDetailDto = TagsDetailDto.valueOf(tags.size(), tags);
        return tagsDetailDto;
    }

    @Override
    public FriendsTypesDto friends() {
        List<FriendsType> friendsTypes = friendsTypeRepository.findAll();
        FriendsTypeDto[] friendsTypeDtos = new FriendsTypeDto[friendsTypes.size()];
        int size = 0;
        for(FriendsType friendsType : friendsTypes) {
            List<Friends> friends = friendsRepository.findAllByTypeId(friendsType.getId());
            FriendsTypeDto friendsTypeDto = FriendsTypeDto.toDTO(friendsType, friends);
            friendsTypeDtos[size] = friendsTypeDto;
            size ++;
        }
        return FriendsTypesDto.valueOf(friendsTypeDtos);
    }

    @Override
    public SearchDto search(String searchValue, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "aid");
        List<Article> articles = articleRepository.findAllByTitleContainingAndStatus(searchValue, ArticleStatusEnum.NORMAL.getStatus(), pageable);
        List<ArticleDetailDto> articleDetailDtos = new ArrayList<>();
        for(Article article : articles) {
            Category category = categoryRepository.findCategoryById(article.getCategoryId());

            List<Tag> tags = new ArrayList<>();

            List<ArticleTagMapper> articleTagMappers = articleTagMapperRepository.findAllByArticleId(article.getId());
            for(ArticleTagMapper articleTagMapper : articleTagMappers) {
                Tag tag = tagRepository.findTagById(articleTagMapper.getTagId());
                tags.add(tag);
            }
            ArticleDetailDto articleDetailDto = ArticleDetailDto.valueOf(article, category, tags, null, null);
            articleDetailDtos.add(articleDetailDto);
        }
        ArticlesDto articlesDto = ArticlesDto.valueOf(page, pageSize, articles.size(), articleDetailDtos);
        SearchDto searchDto = SearchDto.valueOf(page, pageSize, articleDetailDtos.size(), articleDetailDtos);
        return searchDto;
    }

    @Override
    public ArchivesDetailDto archives(int page, int pageSize) {
        Map<String, Map<String, List<ArticleDetailDto>>> yearsMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return -o1.compareTo(o2);
            }
        });
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "publishTime");
        List<Article> articles = articleRepository.findAllByStatus(ArticleStatusEnum.NORMAL.getStatus(), pageable);
        for(Article article : articles) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(article.getPublishTime() * 1000));
            String year = calendar.get(Calendar.YEAR) + "年";
            String month = calendar.get(Calendar.MONTH)  + "月";
            Map<String, List<ArticleDetailDto>> monthsMap = yearsMap.get(year);
            if(monthsMap == null) {
                monthsMap = new HashMap<>();
                yearsMap.put(year, monthsMap);
            }
            List<ArticleDetailDto> articleDetailDtos = monthsMap.get(month);
            if(articleDetailDtos == null) {
                articleDetailDtos = new ArrayList<>();
                monthsMap.put(month, articleDetailDtos);
            }

            Category category = categoryRepository.findCategoryById(article.getCategoryId());
            List<Tag> tags = new ArrayList<>();
            List<ArticleTagMapper> articleTagMappers = articleTagMapperRepository.findAllByArticleId(article.getId());
            for(ArticleTagMapper articleTagMapper : articleTagMappers) {
                Tag tag = tagRepository.findTagById(articleTagMapper.getTagId());
                tags.add(tag);
            }
            ArticleDetailDto articleDetailDto = ArticleDetailDto.valueOf(article, category, tags, null, null);
            articleDetailDtos.add(articleDetailDto);
        }
        return ArchivesDetailDto.valueOf(page, pageSize, yearsMap.size(), yearsMap);
    }

    @Override
    public ResumeDto resume() {
        Pages page = pagesRepository.findPagesByType(PagesTypeEnum.RESUME.getStatusMsg());
        return ResumeDto.toDto(page);
    }
}
