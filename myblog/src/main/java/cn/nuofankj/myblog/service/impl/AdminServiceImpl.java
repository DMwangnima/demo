package cn.nuofankj.myblog.service.impl;

import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.entity.Admin;
import cn.nuofankj.myblog.entity.Article;
import cn.nuofankj.myblog.repository.*;
import cn.nuofankj.myblog.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

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

    // TODO 暂时缺少session操作以及生成各种参数的操作
    @Override
    public AdminUserDto login(String username, String password) {
        Admin admin = adminRepository.findAllByUsername(username);
        if(admin.getPassword() == password) {
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
        return null;
    }

    @Override
    public SysListDto sysLog(int page, int pageSize) {
        return null;
    }

    @Override
    public AdminArticleListDto articleList(String by, int status, int page, int pageSize) {
        return null;
    }

    @Override
    public CategoryListDto categoryList(String all) {
        return null;
    }

    @Override
    public TagListDto tagList(String all) {
        return null;
    }

    @Override
    public CommentListDto commentsList(int page, int pageSize) {
        return null;
    }

    @Override
    public FriendListDto friendList(int page, int pageSize) {
        return null;
    }
}
