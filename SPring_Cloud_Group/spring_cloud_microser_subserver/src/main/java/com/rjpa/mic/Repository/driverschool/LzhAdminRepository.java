package com.rjpa.mic.repository.driverschool;


import com.rjpa.mic.repository.driverschool.Entity.LzhAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LzhAdminRepository extends JpaRepository<LzhAdminEntity, Integer> {

    List<LzhAdminEntity> findByUserName(String username);

    List<LzhAdminEntity> findByPhoneNumber(String phone);
}