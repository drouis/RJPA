package com.rjpa.feign.client;

import com.rjpa.feign.hystrix.PermissionHystrixClientFallBackFactory;
import feign.Headers;
import feign.Param;
import model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vo.SmsMessageV;

@FeignClient(name = "spring-cloud-systools-server", path = "/micoSysToolsServer", fallbackFactory = PermissionHystrixClientFallBackFactory.class)
public interface SystemToolsServiceFeignClient {
    /**
     * 更新短信发送状态
     *
     * @param
     * @return
     */
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @RequestMapping(method = RequestMethod.POST, value = "feignAPI/saveUserSendedSMSFeignAPI",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Result postSaveUserSendedSMSFeignAPI(@Param(value = "data") SmsMessageV vo);
}
