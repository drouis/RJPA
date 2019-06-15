package com.rjpa.feign.client;

import com.rjpa.feign.hystrix.UserHystrixClientFallBackFactory;
import feign.Headers;
import model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wood", path = "/micoSysToolsServer", fallbackFactory = UserHystrixClientFallBackFactory.class)
public interface SystemToolsServiceFeignClient {
    public final static String USER_FEIGN_CLIENT_NAME = "wood-systools-server";

    /**
     * 验证短信
     * @param phonenum
     * @param syoFlg
     * @param smsVerifyCode
     * @return
     */
    @Headers({"Content-Type:application/json", "Accept: application/json"})
    @RequestMapping(method = RequestMethod.POST, value = "/loginVerifyAPI")
    public Result loginVerifyAPI(@RequestParam(value = "phonenum") String phonenum,
                                 @RequestParam(value = "syoFlg") Integer syoFlg,
                                 @RequestParam(value = "smsVerifyCode") String smsVerifyCode);
}
