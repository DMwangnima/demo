package cn.nuofankj.myblog.service.impl;

import cn.nuofankj.myblog.constant.ArticleStatusEnum;
import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.entity.*;
import cn.nuofankj.myblog.repository.*;
import cn.nuofankj.myblog.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    // TODO status暂时不知道如何使用
    @Override
    public AdminArticleListDto articleList(String by, int status, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        List<Article> allByStatus = articleRepository.findAllByStatus(status, pageable);
        return AdminArticleListDto.valueOf(page, pageSize, allByStatus);
    }

    // TODO 此处 all 暂时不知道如何使用，其次是CategoryListDto里边的参数几个不知道干嘛的
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

    // TODO 此处 all 暂时不知道如何使用，其次是CategoryListDto里边的参数几个不知道干嘛的
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


}
