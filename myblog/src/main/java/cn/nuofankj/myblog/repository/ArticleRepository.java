package cn.nuofankj.myblog.repository;


import cn.nuofankj.myblog.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    Article findArticleById(String id);

    List<Article> findByAidLessThan(long aid);

    List<Article> findByAidGreaterThan(long aid);

    List<Article> findAllByTitleContaining(String title, Pageable pageable);

}
