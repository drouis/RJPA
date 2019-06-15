package com.rjpa.repository;


import com.rjpa.repository.Entity.LzhAdminEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@CacheConfig(cacheNames = "lzhAdminEntity")
public interface LzhAdminRepository extends JpaRepository<LzhAdminEntity, Integer> {
    //    @Cacheable
    LzhAdminEntity findById(int id);

    //    @Cacheable
    List<LzhAdminEntity> findByUserName(String username);

    //    @Cacheable
    List<LzhAdminEntity> findByPhoneNumber(String phone);

    //    @Cacheable
    List<LzhAdminEntity> findByUserNameOrPhoneNumber(
            @Param(value = "userName") String userName,
            @Param(value = "phoneNumber") String phoneNumber);

    LzhAdminEntity findByUserUuidEquals(@Param(value = "userUuid") String userUuid);
}