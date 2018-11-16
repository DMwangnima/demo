package cn.nuofankj.myblog.repository;

import cn.nuofankj.myblog.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findAllByTypeId(long typeId);
    Friends findFriendsByFriendId(String friendId);
}
