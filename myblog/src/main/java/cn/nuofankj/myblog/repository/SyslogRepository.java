package cn.nuofankj.myblog.repository;

import cn.nuofankj.myblog.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyslogRepository extends JpaRepository<SysLog, Long> {
}
