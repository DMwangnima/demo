package cn.nuofankj.myblog.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.nuofankj.myblog.entity.Admin;
import cn.nuofankj.myblog.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "accessToken";

    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        addInterceptor.excludePathPatterns("/**");

        // 排除配置
//        addInterceptor.excludePathPatterns("/blogapi/index.php/w/*");
//        addInterceptor.excludePathPatterns("/blogapi/index.php/a/login");
//        addInterceptor.excludePathPatterns("/blogapi/index.php/a/error");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            String accessToken = request.getHeader(SESSION_KEY);
            if (accessToken != null) {
                Admin admin = adminRepository.findAllByAccessToken((String)accessToken);
                if(admin != null) {
                    return true;
                }
            }
            String url = "/blogapi/index.php/a/error";
            response.sendRedirect(url);
            return false;
        }
    }
}