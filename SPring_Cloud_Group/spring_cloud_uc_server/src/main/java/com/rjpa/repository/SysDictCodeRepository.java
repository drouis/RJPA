package com.rjpa.repository;

import com.rjpa.repository.Entity.SysDictcodeEntity;
import feign.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "sysDictCode")
public interface SysDictCodeRepository extends JpaRepository<SysDictcodeEntity, Integer> {

    SysDictcodeEntity findByDid(int did);

    /**
     * 根据书籍主键查询书籍详细信息
     */
    @Query(value = "SELECT * FROM sys_dictcode WHERE 1 = 1 AND dpresent = :dpresent ORDER BY did ASC ", nativeQuery = true)
    List<SysDictcodeEntity> selectGroupByDpresent(@Param(value = "dpresent") String dpresent);

    List<SysDictcodeEntity> findByDpresent(@Param(value = "dpresent") String dpresent);

    List<SysDictcodeEntity> findByDcolumn(@Param(value = "dcolumn") String dcolumn);
}
