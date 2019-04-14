package com.rjpa.repository;


import com.rjpa.repository.Entity.LzhAdminEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LzhAdminRepository extends JpaRepository<LzhAdminEntity, Integer> {
    LzhAdminEntity findById(int id);

    List<LzhAdminEntity> findByUserName(String username);

    List<LzhAdminEntity> findByPhoneNumber(String phone);

    List<LzhAdminEntity> findByUserNameOrPhoneNumber(
            @Param(value = "userName") String userName,
            @Param(value = "phoneNumber") String phoneNumber);
}