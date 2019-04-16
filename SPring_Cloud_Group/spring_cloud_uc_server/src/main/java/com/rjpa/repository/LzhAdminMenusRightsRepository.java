package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;
import feign.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@CacheConfig(cacheNames = "lzhAdminMenusRightsEntity")
public interface LzhAdminMenusRightsRepository extends JpaRepository<LzhAdminMenusRightsEntity, Integer> {
    @Cacheable
    List<LzhAdminMenusRightsEntity> getLzhAdminMenusRightsEntitiesByParentidOrderByClassesAsc(int parentid);

    @Cacheable
    LzhAdminMenusRightsEntity findById(int mid);

    @Cacheable
    List<LzhAdminMenusRightsEntity> findByName(String name);

    @Cacheable
    List<LzhAdminMenusRightsEntity> findByPermission(String permission);

    @Cacheable
    List<LzhAdminMenusRightsEntity> findByParentidAndNameAndPermissionOrderByClassesAsc(@Param(value = "parentid") int parentid,
                                                                                        @Param(value = "name") String name,
                                                                                        @Param(value = "permission") String permission);

    @Cacheable
    List<LzhAdminMenusRightsEntity> findByParentidOrderByClassesAsc(int mid);
}
