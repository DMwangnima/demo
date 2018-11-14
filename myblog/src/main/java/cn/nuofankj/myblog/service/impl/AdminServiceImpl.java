package cn.nuofankj.myblog.service.impl;

import cn.nuofankj.myblog.dto.impl.*;
import cn.nuofankj.myblog.entity.Admin;
import cn.nuofankj.myblog.repository.AdminRepository;
import cn.nuofankj.myblog.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Configuration
@Slf4j
public class AdminServiceImpl implements AdminService, UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public AdminUserDto login(String username, String password) {

        return null;
    }

    @Override
    public long saveArticle(long id, String content, String htmlContent, String title, String cover, String subMessage, String isEncrypt) {
        return 0;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findAllByUsername(username);
        return admin;
    }
}
