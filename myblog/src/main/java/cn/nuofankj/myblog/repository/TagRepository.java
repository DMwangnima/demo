package cn.nuofankj.myblog.repository;

import cn.nuofankj.myblog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagById(String id);
    Tag findAllByName(String name);
}
