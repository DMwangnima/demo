package cn.nuofankj.myblog.repository;

import cn.nuofankj.myblog.entity.BlogConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogConfigRepository extends JpaRepository<BlogConfig, Long> {
}
