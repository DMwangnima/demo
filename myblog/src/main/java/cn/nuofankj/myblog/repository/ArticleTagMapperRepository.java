package cn.nuofankj.myblog.repository;


import cn.nuofankj.myblog.entity.ArticleTagMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ArticleTagMapperRepository extends JpaRepository<ArticleTagMapper, Long> {
     List<ArticleTagMapper> findAllByArticleId(String articleId);
     List<ArticleTagMapper> deleteAllByArticleId(String articleId);
     List<ArticleTagMapper> findAllByTagId(String tagId, Pageable pageable);
}
