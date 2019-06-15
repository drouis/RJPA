package com.rjpa.feign.hystrix;

import com.rjpa.feign.client.SystemToolsServiceFeignClient;
import com.rjpa.feign.client.SystemToolsServiceFeignClientWithFactory;
import feign.hystrix.FallbackFactory;
import model.Result;
import model.utils.SystemConstCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import vo.SmsMessageV;

@Component
public class SystemToolsHystrixClientFallBackFactory implements FallbackFactory<SystemToolsServiceFeignClient> {
    private static final Logger logger = LoggerFactory.getLogger(SystemToolsHystrixClientFallBackFactory.class);

    public static final String ERROR_FALLBACK_STR = "来自 HystrixFallBackFactory 的系统消息:";


    @Override
    public SystemToolsServiceFeignClient create(Throwable throwable) {
        SystemToolsHystrixClientFallBackFactory.logger.info("fallback reason was: {} ", throwable.getMessage());
        return new SystemToolsServiceFeignClientWithFactory() {

            @Override
            public Result postSaveUserSendedSMSFeignAPI(SmsMessageV vo) {
                return Result.error(SystemConstCode.PARA_ERROR.getCode(), ERROR_FALLBACK_STR + SystemConstCode.PARA_ERROR.getMessage());
            }
        };
    }
}
