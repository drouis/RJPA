package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminPermissionEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// @CacheConfig(cacheNames = "lzhAdminPermissionEntity")
public interface LzhAdminPermissionRepository extends JpaRepository<LzhAdminPermissionEntity, Integer> {
//    @Cacheable
    LzhAdminPermissionEntity findById(int id);

//    @Cacheable
    List<LzhAdminPermissionEntity> findByPermission(String permission);

//    @Cacheable
    List<LzhAdminPermissionEntity> findByName(String name);

//    @Cacheable
    List<LzhAdminPermissionEntity> findByDescription(String description);

//    @Cacheable
    List<LzhAdminPermissionEntity> findByPermissionUrlLike(String permissionUrl);

//    @Cacheable
    LzhAdminPermissionEntity findByIdAndAndDescriptionNotNull(int id);
}