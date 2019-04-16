package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminRolePermissionEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@CacheConfig(cacheNames = "lzhAdminRolePermissionEntity")
public interface LzhAdminRolePermissionRepository extends JpaRepository<LzhAdminRolePermissionEntity, Integer> {
    @Cacheable
    List<LzhAdminRolePermissionEntity> findByRoleId(long roleId);

    @Transactional
    void deleteAllByRoleId(long roleId);
}
