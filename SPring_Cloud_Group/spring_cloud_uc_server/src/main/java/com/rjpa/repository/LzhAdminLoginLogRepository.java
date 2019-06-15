package com.rjpa.repository;

import com.rjpa.repository.Entity.LzhAdminLoginLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LzhAdminLoginLogRepository extends JpaRepository<LzhAdminLoginLogEntity, Integer> {
    public LzhAdminLoginLogEntity getLzhAdminLoginLogEntityByLoginnameEquals(String loginname);
}
