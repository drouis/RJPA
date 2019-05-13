package com.rjpa.mic.controller;

import com.rjpa.mic.repository.dao.CteQicaiMapper;

import com.rjpa.mic.repository.model.CteQicai;
import com.rjpa.mic.repository.model.CteQicaiExample;
import model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 配置断路器，当路由分配请求出险问题后反馈到eureka，提醒服务错误
 */
@RestController
public class MicoUserMainController {

    @RequestMapping(method = RequestMethod.GET, value = "/hi")
    public Result sayHi(@RequestParam(value = "name", required = false) String name) {
        Result r = Result.ok(null);
        String instanceStr = getLocalInstanceInfo();
        r.setMsg("ClientName: MIcoUserSErverInterface main Nodes" + ",ServerName: MainServer");
        return r;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find/{id}")
    public Result findByUserId(@PathVariable Integer id) {
        Result r = Result.ok(null);
        return r;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findQiCai/{qicai}")
    public Result findQicaiByName(@PathVariable(value = "qicai") String name){
        CteQicaiExample qExample = new CteQicaiExample();
        CteQicaiExample.Criteria qc =  qExample.createCriteria();
        qc.andQnameEqualTo(name);
        qicaiMapper.findByQname(name);
        List<CteQicai> vList = qicaiMapper.selectByExample(qExample);
        Result r = Result.ok(vList);
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
    CteQicaiMapper qicaiMapper;
}