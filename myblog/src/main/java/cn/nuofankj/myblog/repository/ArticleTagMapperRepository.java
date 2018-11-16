package cn.nuofankj.myblog.repository;


import cn.nuofankj.myblog.entity.ArticleTagMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagMapperRepository extends JpaRepository<ArticleTagMapper, Long> {
     List<ArticleTagMapper> findAllByArticleId(String articleId);
     List<ArticleTagMapper> deleteByArticleId(String articleId);
}
