package com.rjpa.feign.hystrix;

import com.rjpa.feign.client.PermissionFeignClientWithFactory;
import com.rjpa.feign.client.PermissionServiceFeignClient;
import feign.hystrix.FallbackFactory;
import model.Result;
import model.utils.SystemConstCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PermissionHystrixClientFallBackFactory implements FallbackFactory<PermissionServiceFeignClient> {
    private static final Logger logger = LoggerFactory.getLogger(PermissionHystrixClientFallBackFactory.class);

    public static final String ERROR_FALLBACK_STR = "来自 HystrixFallBackFactory 的系统消息:";

    @Override
    public PermissionServiceFeignClient create(Throwable throwable) {
        PermissionHystrixClientFallBackFactory.logger.info("fallback reason was: {} ", throwable.getMessage());
        return new PermissionFeignClientWithFactory() {

            @Override
            public Result postSubMicoServerPermissions(Result r) {
                return Result.error(SystemConstCode.PARA_ERROR.getCode(), ERROR_FALLBACK_STR + SystemConstCode.PARA_ERROR.getMessage());
            }
        };
    }
}
