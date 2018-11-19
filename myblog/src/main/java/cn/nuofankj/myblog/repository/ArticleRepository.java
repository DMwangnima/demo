package cn.nuofankj.myblog.repository;


import cn.nuofankj.myblog.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    Article findArticleById(String id);

    List<Article> findByAidLessThanAndStatus(long aid, long status, Pageable pageable);

    List<Article> findByAidGreaterThanAndStatus(long aid, long status, Pageable pageable);

    List<Article> findAllByTitleContainingAndStatus(String title, long status, Pageable pageable);

    List<Article> findAllByStatus(long status, Pageable pageable);

    List<Article> findAllByStatusAndCategoryId(long status, String categoryId, Pageable pageable);

    List<Article> findAllByStatusAndIdNotContains(long status, Pageable pageable, String id);

    List<Article> findAllByStatus(long status);
}
