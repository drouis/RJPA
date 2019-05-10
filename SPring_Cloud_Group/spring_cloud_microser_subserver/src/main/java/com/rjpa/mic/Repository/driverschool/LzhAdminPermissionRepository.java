package com.rjpa.mic.repository.driverschool;

import com.rjpa.mic.repository.driverschool.Entity.LzhAdminPermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LzhAdminPermissionRepository extends JpaRepository<LzhAdminPermissionEntity, Integer> {
}