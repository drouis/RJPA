package com.rjpa.repository;

import com.rjpa.repository.Entity.AmpqMessageEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmpqMessageRepository extends JpaRepository<AmpqMessageEntity, Integer> {
    AmpqMessageEntity findByUuid(@Param(value = "uuid") String uuid);
}
