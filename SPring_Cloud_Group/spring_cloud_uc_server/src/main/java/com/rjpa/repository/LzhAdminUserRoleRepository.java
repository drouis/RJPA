package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LzhAdminUserRoleRepository extends JpaRepository<LzhAdminUserRoleEntity, Integer> {
    public List<LzhAdminUserRoleEntity> findByRoleId(long roleId);
}
