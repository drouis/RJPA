package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminRoleEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@CacheConfig(cacheNames = "lzhAdminRoleEntity")
public interface LzhAdminRoleRepository extends JpaRepository<LzhAdminRoleEntity, Integer> {
//    @Cacheable
    List<LzhAdminRoleEntity> findByName(String name);

//    @Cacheable
    List<LzhAdminRoleEntity> findByRole(String role);

//    @Cacheable
    LzhAdminRoleEntity findById(int rid);
}
