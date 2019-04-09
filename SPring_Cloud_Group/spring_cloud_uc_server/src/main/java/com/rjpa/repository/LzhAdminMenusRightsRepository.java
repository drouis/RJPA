package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminMenusRightsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LzhAdminMenusRightsRepository extends JpaRepository<LzhAdminMenusRightsEntity, Integer> {

    List<LzhAdminMenusRightsEntity> getLzhAdminMenusRightsEntitiesByParentid(int parentid);

}
