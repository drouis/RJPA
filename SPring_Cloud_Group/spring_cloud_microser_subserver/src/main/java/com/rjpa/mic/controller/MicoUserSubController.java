package com.rjpa.mic.controller;

import com.rjpa.mic.Repository.Entity.LzhAdminEntity;
import com.rjpa.mic.Repository.Entity.LzhAdminPermissionEntity;
import com.rjpa.mic.Repository.LzhAdminPermissionRepository;
import com.rjpa.mic.Repository.LzhAdminRepository;
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

    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    public Result sayHi(@RequestParam(value = "name", required = false) String name) {
        Result r = Result.ok(null);
        String instanceStr = getLocalInstanceInfo();
        r.setMsg("ClientName: MIcoUserSErverInterface sub Nodes" + ",ServerName: SubServer");
        return r;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/find/{id}")
    public Result findByUserId(@PathVariable Integer id) {
        Optional<LzhAdminEntity> u = lzhAdminRepository.findById(id);
        Result r = Result.ok(u);
        return r;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/urls")
    public Result findAllAdminPermission() {
        List<LzhAdminPermissionEntity> p = lzhAdminPermissionRepository.findAll();
        Result r = Result.ok(p);
        return r;
    }

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

    @Autowired
    LzhAdminRepository lzhAdminRepository;
    @Autowired
    LzhAdminPermissionRepository lzhAdminPermissionRepository;
}