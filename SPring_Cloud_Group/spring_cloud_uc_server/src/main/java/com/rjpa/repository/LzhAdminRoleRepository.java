package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LzhAdminRoleRepository extends JpaRepository<LzhAdminRoleEntity, Integer> {
    List<LzhAdminRoleEntity> findByName(String name);

    List<LzhAdminRoleEntity> findByRole(String role);

    LzhAdminRoleEntity findById(int rid);
}
