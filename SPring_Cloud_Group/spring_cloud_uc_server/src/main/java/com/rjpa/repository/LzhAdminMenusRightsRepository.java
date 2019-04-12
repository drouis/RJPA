package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LzhAdminMenusRightsRepository extends JpaRepository<LzhAdminMenusRightsEntity, Integer> {

    List<LzhAdminMenusRightsEntity> getLzhAdminMenusRightsEntitiesByParentidOrderByClassesAsc(int parentid);

    LzhAdminMenusRightsEntity findById(int mid);

    List<LzhAdminMenusRightsEntity> findByName(String name);

    List<LzhAdminMenusRightsEntity> findByPermission(String permission);


    List<LzhAdminMenusRightsEntity> findByParentidAndNameAndPermissionOrderByClassesClassesAsc(@Param(value = "parentid") int parentid,
                                                                       @Param(value = "name") String name,
                                                                       @Param(value = "permission") String permission);

    List<LzhAdminMenusRightsEntity> findByParentidOrderByClassesAsc(int mid);
}
