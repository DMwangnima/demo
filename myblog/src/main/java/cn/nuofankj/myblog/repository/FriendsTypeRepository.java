package cn.nuofankj.myblog.repository;

import cn.nuofankj.myblog.entity.FriendsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsTypeRepository extends JpaRepository<FriendsType, Long> {
}
