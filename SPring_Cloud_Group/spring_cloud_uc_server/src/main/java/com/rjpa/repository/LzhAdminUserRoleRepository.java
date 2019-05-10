package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminUserRoleEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

//@CacheConfig(cacheNames = "lzhAdminUserRoleEntity")
public interface LzhAdminUserRoleRepository extends JpaRepository<LzhAdminUserRoleEntity, Integer> {
//    @Cacheable
    public List<LzhAdminUserRoleEntity> findByRoleId(long roleId);

//    @Cacheable
    public List<LzhAdminUserRoleEntity> findByUserId(long userId);

    @Transactional
    public void deleteByRoleId(long roleId);
}
