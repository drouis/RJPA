package com.rjpa.mic.controller;


import com.rjpa.mic.repository.driverschool.Entity.LzhAdminEntity;
import com.rjpa.mic.repository.driverschool.Entity.LzhAdminPermissionEntity;
import com.rjpa.mic.repository.driverschool.LzhAdminPermissionRepository;
import com.rjpa.mic.repository.driverschool.LzhAdminRepository;
import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 配置断路器，当路由分配请求出险问题后反馈到eureka，提醒服务错误
 */
@RestController
public class MicoUserSubController {

    @Autowired
    private DiscoveryClient discoveryClient;

    private String getLocalInstanceInfo() {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("spring.cloud.micoUSer.server");
        if (null != serviceInstances && 0 < serviceInstances.size()) {
            ServiceInstance serviceInstance = serviceInstances.get(0);
            return serviceInstance.getServiceId() + ":" + serviceInstance.getHost() + ":" + serviceInstance.getPort();
        } else {
            return "Instance HostName Error!";
        }
    }
}