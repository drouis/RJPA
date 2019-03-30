package com.rjpa.mic.Repository;


import com.rjpa.mic.Repository.Entity.LzhAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LzhAdminRepository extends JpaRepository<LzhAdminEntity, Integer> {

    List<LzhAdminEntity> findByUserName(String username);

    List<LzhAdminEntity> findByPhoneNumber(String phone);
}