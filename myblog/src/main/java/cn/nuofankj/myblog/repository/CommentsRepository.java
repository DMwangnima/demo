package cn.nuofankj.myblog.repository;

import cn.nuofankj.myblog.entity.Comments;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByArticleIdAndIsAuthor(String articleId, int isAuthor, Pageable pageable);
    List<Comments> findAllByParentId(long parentId, Pageable pageable);
    List<Comments> findAllByParentIdAndArticleId(long parentId, String articleId, Pageable pageable);
}
