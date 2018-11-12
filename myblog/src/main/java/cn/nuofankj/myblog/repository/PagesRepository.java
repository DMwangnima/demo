package cn.nuofankj.myblog.repository;

import cn.nuofankj.myblog.entity.Pages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagesRepository extends JpaRepository<Pages, Long> {
    Pages findPagesByType(String type);
}
