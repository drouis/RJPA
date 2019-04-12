package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminRolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface LzhAdminRolePermissionRepository extends JpaRepository<LzhAdminRolePermissionEntity, Integer> {

    List<LzhAdminRolePermissionEntity> findByRoleId(long roleId);

    @Transactional
    void deleteAllByRoleId(long roleId);
}
