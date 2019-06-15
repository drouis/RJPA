package com.rjpa.feign.client;

import com.rjpa.feign.hystrix.PermissionHystrixClientFallBackFactory;
import feign.Headers;
import feign.Param;
import model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "spring-cloud-mico-usercenter-server", path = "/micoUSC", fallbackFactory = PermissionHystrixClientFallBackFactory.class)
public interface PermissionServiceFeignClient {

    /**
     * 提交当前子服务中的所有权限内容
     *
     * @param
     * @return
     */
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @RequestMapping(method = RequestMethod.POST, value = "/subPermissionAPI/postSubMicoServerPermissions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Result postSubMicoServerPermissions(@Param(value = "data") Result r);
}
