package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LzhAdminPermissionRepository extends JpaRepository<LzhAdminPermissionEntity, Integer> {
    LzhAdminPermissionEntity findById(int id);

    List<LzhAdminPermissionEntity> findByPermission(String permission);

    List<LzhAdminPermissionEntity> findByName(String name);

    List<LzhAdminPermissionEntity> findByDescription(String description);
}