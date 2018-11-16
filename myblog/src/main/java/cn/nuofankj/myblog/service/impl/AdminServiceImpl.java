package cn.nuofankj.myblog.service.impl;

import cn.nuofankj.myblog.constant.ArticleStatusEnum;
import cn.nuofankj.myblog.constant.CategoryStatusEnum;
import cn.nuofankj.myblog.constant.PagesTypeEnum;
import cn.nuofankj.myblog.constant.TagStatusEnum;
import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.entity.*;
import cn.nuofankj.myblog.pojo.TagsPojo;
import cn.nuofankj.myblog.repository.*;
import cn.nuofankj.myblog.service.AdminService;
import cn.nuofankj.myblog.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Configuration
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;
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
    @Autowired
    private SyslogRepository syslogRepository;

    // TODO 暂时缺少session操作以及生成各种参数的操作
    @Override
    public AdminUserDto login(String username, String password) {
        Admin admin = adminRepository.findAllByUsername(username);
        if(admin != null) {
            return AdminUserDto.valueOf(admin.getUserId(), admin.getUsername(), admin.getLastLoginTime(), admin.getAccessToken(), admin.getTokenExpiresIn(), null);
        }
        return null;
    }

    @Override
    public String saveArticle(String id, String content, String htmlContent, String title, String cover, String subMessage, String isEncrypt) {
        Article article = articleRepository.findArticleById(id);
        if(article == null){
            return null;
        }
        article.setContent(content);
        article.setHtmlContent(htmlContent);
        article.setTitle(title);
        article.setCover(cover);
        article.setSubMessage(subMessage);
        article.setIsEncrypt(isEncrypt);
        return article.getId();
    }

    @Override
    public StatisticsDto statisticsHome() {
        int publishCount = articleRepository.findAllByStatus(ArticleStatusEnum.NORMAL.getStatus()).size();
        int deletedCount = articleRepository.findAllByStatus(ArticleStatusEnum.DELETE.getStatus()).size();
        int draftsCount = articleRepository.findAllByStatus(ArticleStatusEnum.RRECORD.getStatus()).size();
        long categoryCount = categoryRepository.count();
        long tagCount = tagRepository.count();
        long commentsCount = commentsRepository.count();
        return StatisticsDto.valueOf(publishCount, draftsCount, deletedCount, categoryCount, tagCount, commentsCount);
    }

    @Override
    public SysListDto sysLog(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<SysLog> sysLogs = syslogRepository.findAll(pageable);
        return SysListDto.valueOf(page, pageSize, sysLogs.getContent());
    }

    @Override
    public AdminArticleListDto articleList(String by, int status, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        List<Article> allByStatus = articleRepository.findAllByStatus(status, pageable);
        return AdminArticleListDto.valueOf(page, pageSize, allByStatus);
    }

    @Override
    public CategoryListDto categoryList(String all) {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDescDto> categoryDescDtos = new ArrayList<>();
        for(Category category  : categories) {
            CategoryDescDto categoryDescDto = new CategoryDescDto(category.getId(), category.getName(), category.getCreateTime(), category.getUpdateTime(), category.getStatus(), category.getArticleCount(), category.getCanDel());
            categoryDescDtos.add(categoryDescDto);
        }
        return new CategoryListDto(categoryDescDtos);
    }

    @Override
    public TagListDto tagList(String all) {
        List<Tag> tags = tagRepository.findAll();
        List<TagDescDto> tagDescDtos = new ArrayList<>();
        for(Tag tag : tags) {
            tagDescDtos.add(new TagDescDto(tag.getId(),tag.getName(),tag.getCreateTime(),tag.getUpdateTime(),tag.getStatus(),tag.getArticleCount()));
        }
        return new TagListDto(tagDescDtos);
    }

    @Override
    public CommentListDto commentsList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<Comments> comments = commentsRepository.findAll(pageable);
        return CommentListDto.valueOf(page, pageSize, comments.getSize(), comments.getContent());
    }

    @Override
    public FriendListDto friendList(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<Friends> friends = friendsRepository.findAll(pageable);
        return FriendListDto.valueOf(page, pageSize, friends.getSize(), friends.getContent());
    }

    @Override
    public String publish(String id, String content, String htmlContent, String title, String cover, String subMessage, String isEncrypt, String category, String tags) {
        Article article = new Article();
        article.setId(id);
        article.setContent(content);
        article.setHtmlContent(htmlContent);
        article.setTitle(title);
        article.setCover(cover);
        article.setSubMessage(subMessage);
        article.setIsEncrypt(isEncrypt);
        article = articleRepository.save(article);
//        Tag allByName = tagRepository.findAllByName(tags);
        return null;

    }

    // TODO 此处待实现
    @Override
    public String qiniuToken(String bucket, String withWater) {
        return "test";
    }

    @Override
    public ArticleDetailDto articleInfo(String id) {
        Article article = articleRepository.findArticleById(id);
        Category category = categoryRepository.findCategoryById(article.getCategoryId());
        List<Tag> tags = new ArrayList<>();
        List<ArticleTagMapper> articleTagMappers = articleTagMapperRepository.findAllByArticleId(article.getId());
        for(ArticleTagMapper articleTagMapper : articleTagMappers) {
            Tag tag = tagRepository.findTagById(articleTagMapper.getTagId());
            tags.add(tag);
        }
        ArticleDetailDto articleDetailDto = ArticleDetailDto.valueOf(article, category, tags, null, null);
        return articleDetailDto;
    }

    @Override
    public String modifyArticle(String title, String cover, String subMessage, String isEncrypt, String content, String htmlContent, String id, String categoryId, TagsPojo[] tags) {
        Article article = articleRepository.findArticleById(id);
        article.setTitle(title);
        article.setCover(cover);
        article.setSubMessage(subMessage);
        article.setIsEncrypt(isEncrypt);
        article.setContent(content);
        article.setHtmlContent(htmlContent);
        article.setCategoryId(categoryId);
        articleRepository.save(article);
        for(TagsPojo pojo : tags) {
            if(pojo.getId() != null && !pojo.getId().equals("")) {
                List<ArticleTagMapper> articleTagMappers = articleTagMapperRepository.deleteByArticleId(id);
                ArticleTagMapper articleTagMapper = new ArticleTagMapper();
                articleTagMapper.setArticleId(id);
                articleTagMapper.setTagId(pojo.getId());
                articleTagMapperRepository.save(articleTagMapper);
                for(ArticleTagMapper tagMapper : articleTagMappers) {
                    Tag tagById = tagRepository.findTagById(tagMapper.getTagId());
                    tagById.setArticleCount(tagById.getArticleCount() - 1);
                    tagById.setUpdateTime(new Date().getTime());
                    tagRepository.save(tagById);
                }
                Tag tagById = tagRepository.findTagById(pojo.getId());
                tagById.setArticleCount(tagById.getArticleCount() + 1);
                tagById.setUpdateTime(new Date().getTime());
                tagRepository.save(tagById);
            }
            if(pojo.getName() != null && !pojo.getName().equals("")) {
                Tag tag = new Tag();
                tag.setUpdateTime(new Date().getTime());
                tag.setArticleCount(1);
                tag.setCreateTime(new Date().getTime());
                tag.setName(pojo.getName());
                tag.setId(CommonUtil.getRandomString(25));
                tagRepository.save(tag);
                ArticleTagMapper articleTagMapper = new ArticleTagMapper();
                articleTagMapper.setArticleId(id);
                articleTagMapper.setTagId(tag.getId());
                articleTagMapperRepository.save(articleTagMapper);
            }
        }
        return article.getId();
    }

    @Override
    public void articleDelete(String id) {
        Article article = articleRepository.findArticleById(id);
        article.setStatus(ArticleStatusEnum.DELETE.getStatus());
        article.setDeleteTime(new Date().getTime());
        articleRepository.save(article);
    }

    @Override
    public String addCategory(String categoryName) {
        Category category = new Category();
        category.setName(categoryName);
        category.setCreateTime(new Date().getTime());
        category.setUpdateTime(new Date().getTime());
        category.setId(CommonUtil.getRandomString(25));
        categoryRepository.save(category);
        return category.getId();
    }

    @Override
    public void modifyCategory(String categoryId, String categoryName) {
        Category category = categoryRepository.findCategoryById(categoryId);
        category.setName(categoryName);
        category.setUpdateTime(new Date().getTime());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findCategoryById(categoryId);
        category.setStatus(CategoryStatusEnum.DELETE.getStatus());
        categoryRepository.save(category);
    }

    @Override
    public String addTag(String tagName) {
        Tag tag = new Tag();
        tag.setUpdateTime(new Date().getTime());
        tag.setCreateTime(new Date().getTime());
        tag.setName(tagName);
        tag.setId(CommonUtil.getRandomString(25));
        tagRepository.save(tag);
        return tag.getId();
    }

    @Override
    public void deleteTag(String tagId) {
        Tag tag = tagRepository.findTagById(tagId);
        tag.setUpdateTime(new Date().getTime());
        tag.setStatus(TagStatusEnum.DELETE.getStatus());
        tagRepository.save(tag);
    }

    @Override
    public void modifyTag(String tagId, String tagName) {
        Tag tag = tagRepository.findTagById(tagId);
        tag.setUpdateTime(new Date().getTime());
        tag.setName(tagName);
        tagRepository.save(tag);
    }

    @Override
    public void replyComments(String articleId, long replyId, String content, String sourceContent) {
        Comments comments = new Comments();
        comments.setArticleId(articleId);
        comments.setReplyId(replyId);
        comments.setContent(content);
        comments.setSourceContent(content);
        comments.setCreateTime(new Date().getTime());
        commentsRepository.save(comments);
    }

    @Override
    public BlogConfigDto webConfig() {
        List<BlogConfig> all = blogConfigRepository.findAll();
        if(all.size() == 0) {
            log.error("BlogConfig为空，要报错啦！！！");
            return null;
        }
        // TODO 暫時取第一個
        BlogConfig blogConfig = all.get(0);
        BlogConfigDto blogConfigDto = BlogConfigDto.toDTO(blogConfig, 0, 0, 0);
        return blogConfigDto;
    }

    @Override
    public PageMDDto getAbout() {
        Pages page = pagesRepository.findPagesByType(PagesTypeEnum.ABOUT.getStatusMsg());
        return new PageMDDto(page.getType(), page.getMd());
    }

    @Override
    public PageMDDto getResume() {
        Pages page = pagesRepository.findPagesByType(PagesTypeEnum.RESUME.getStatusMsg());
        return new PageMDDto(page.getType(), page.getMd());    }

    @Override
    public void modifyFriends(String name, String url, int type, String friendId) {
        Friends friends = friendsRepository.findFriendsByFriendId(friendId);
        friends.setName(name);
        friends.setUrl(url);
        friends.setTypeId(type);
        friendsRepository.save(friends);
    }

    @Override
    public void deleteFriends(String friendId) {
        Friends friends = friendsRepository.findFriendsByFriendId(friendId);
        friendsRepository.delete(friends);
    }

    @Override
    public void deleteComments(long id) {
        commentsRepository.deleteById(id);
    }
}
