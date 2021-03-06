package com.rjpa.mic.repository.driverschool;

import com.rjpa.mic.repository.driverschool.Entity.AmpqMessageEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmpqMessageRepository extends JpaRepository<AmpqMessageEntity, Integer> {

    List<AmpqMessageEntity> findByUuid(@Param(value = "uuid") String uuid);
}
